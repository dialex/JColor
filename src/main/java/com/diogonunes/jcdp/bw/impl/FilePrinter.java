package com.diogonunes.jcdp.bw.impl;

import com.diogonunes.jcdp.bw.api.AbstractPrinter;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class is a File implementation of the Printer interface, hence all
 * output is sent to a file. It implements all abstract methods
 * inherited from the {@link AbstractPrinter} class.
 * <br>
 * This allows one to use JCDP as an actual logging mechanism. A SLF4J adapter
 * is available separately, which can be configured to log simultaneously to
 * terminal and file with one call. See <a href="https://github.com/toyg/slf4j-jcdp">SLF4J-JCDP</a> for details.
 *
 * @author Giacomo Lacava
 * @version 2.1
 */
public class FilePrinter extends AbstractPrinter {

    // =========
    // THREADING
    // =========

    private class CleanupThread extends Thread {
        private final LockableWriter _writer;

        CleanupThread(LockableWriter theWriter) {
            _writer = theWriter;
        }

        @Override
        public void run() {
            _writer.close();
        }
    }

    /**
     * writer using a {@link ReentrantLock} for safe multithreading
     */
    private class LockableWriter implements AutoCloseable, Flushable, Appendable {
        private ReentrantLock lock;
        private OutputStream outStream;
        private PrintWriter writer;
        private File file;
        private boolean open = true;

        /**
         * constructor
         *
         * @param file {@link File} for output
         * @throws FileNotFoundException if file cannot be found
         */
        LockableWriter(File file) throws FileNotFoundException, SecurityException {
            Charset charset;
            try {
                charset = Charset.forName("UTF-8");
            } catch (IllegalArgumentException e) {
                // utf not available, use default
                charset = Charset.defaultCharset();
            }
            this.file = file;
            this.lock = new ReentrantLock();
            this.outStream = new FileOutputStream(file, true);
            this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outStream, charset)));
            // register a cleanup job to close streams
            Runtime.getRuntime().addShutdownHook(new CleanupThread(this));
        }

        /**
         * guard against threads trying to write to closed streams
         */
        boolean isOpen() {
            return this.open;
        }

        /**
         * write string to file
         *
         * @param str the string to output
         */
        void write(String str) {
            lock.lock();
            if (this.isOpen())
                writer.write(str);
            lock.unlock();
        }

        @Override
        public void flush() {
            lock.lock();
            if (this.isOpen())
                writer.flush();
            lock.unlock();
        }

        @Override
        public Appendable append(CharSequence csq) {
            lock.lock();
            if (this.isOpen())
                writer.append(csq);
            lock.unlock();
            return this;
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) {
            lock.lock();
            if (this.isOpen())
                writer.append(csq, start, end);
            lock.unlock();
            return this;
        }

        @Override
        public Appendable append(char c) {
            lock.lock();
            if (this.isOpen())
                writer.append(c);
            lock.unlock();
            return this;
        }

        /**
         * cleanup operations, will be called on shutdown
         */
        public void close() {
            lock.lock();
            try {
                if (this.writer != null) this.writer.close();
                if (this.outStream != null) this.outStream.close();
            } catch (IOException e) {
                // at this point outStream is gone, so it doesn't matter
            }
            this.open = false;
            lock.unlock();
        }

        /**
         * accessor to file details
         */
        File getFile() {
            return file;
        }
    }

    /* we keep a static map of file paths -> threadsafe output streams,
     * so multiple instances can orderly write to the same file. */
    static private HashMap<String, LockableWriter> lockRegistry = new HashMap<>();

    // the writer for this instance
    private LockableWriter writer;

    /**
     * Constructor using builder.
     *
     * @param builder {@link Builder} with the desired configuration
     * @throws FileNotFoundException if the file cannot be created or accessed
     * @throws SecurityException     if the file cannot be created or accessed
     */
    public FilePrinter(Builder builder) throws FileNotFoundException, SecurityException {
        setFile(builder._logFile);
        setLevel(builder._level);
        setTimestamping(builder._timestampFlag);
        setDateFormat(builder._dateFormat);
    }

    private void setFile(File outFile) throws FileNotFoundException, SecurityException {
        // if there is no writer associated to this file, create it;
        // then retrieve a reference
        lockRegistry.putIfAbsent(outFile.getAbsolutePath(), new LockableWriter(outFile));
        this.writer = lockRegistry.get(outFile.getAbsolutePath());
        //TODO replace this.writer with _writer
    }

    // =========
    // BUILDER
    // =========

    /**
     * Builder pattern: allows the caller to specify the attributes that it
     * wants to change and keep the default values ​​in the others.
     */
    public static class Builder {
        // required parameters
        private File _logFile;
        private int _level;
        private boolean _timestampFlag;
        // optional parameters, initialized to default values
        private DateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /**
         * The Printer created uses, by default, timestamping format according
         * to ISO 8601.
         *
         * @param logFile {@link java.io.File} to log messages
         * @param level   specifies the maximum level of debug this printer can
         *                print.
         * @param tsFlag  true, if you want a timestamp before each message.
         * @see <a href=
         * "http://www.iso.org/iso/catalogue_detail.htm?csnumber=26780">ISO
         * 8601</a>
         */
        public Builder(File logFile, int level, boolean tsFlag) {
            _logFile = logFile;
            _level = level;
            _timestampFlag = tsFlag;
        }

        /**
         * @param df the printing format of the timestamp.
         * @return the builder.
         */
        public Builder withFormat(DateFormat df) {
            this._dateFormat = df;
            return this;
        }

        /**
         * @return a new instance of TerminalPrinter.
         */
        public FilePrinter build() throws FileNotFoundException, SecurityException {
            return new FilePrinter(this);
        }
    }

    private void printWithLevel(Object msg, int level) {
        if (isLoggingTimestamps()) printTimestamp();
        this.writer.write("[ " + level + " ] " + msg.toString());
    }

    // =================================
    // OTHER METHODS (implementations)
    // =================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void printTimestamp() {
        this.writer.write(getDateFormatted() + " ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printErrorTimestamp() {
        this.writer.write(getDateFormatted() + " ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(Object msg) {
        if (isLoggingTimestamps())
            printTimestamp();
        this.writer.write(msg.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(Object msg) {
        if (isLoggingTimestamps())
            printTimestamp();
        this.writer.write(msg.toString() + System.lineSeparator());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrint(Object msg) {
        print(msg.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrintln(Object msg) {
        println(msg.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg) {
        if (isLoggingDebug()) print(msg.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg, int level) {
        if (isLoggingDebug() && canPrint(level))
            printWithLevel(msg.toString(), level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg) {
        if (isLoggingDebug())
            print(msg.toString() + System.lineSeparator());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg, int level) {
        if (isLoggingDebug() && canPrint(level))
            printWithLevel(msg.toString() + System.lineSeparator(), level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " | level: " + getLevel()
                + " | timestamping: " + isLoggingTimestamps()
                + " | " + writer.getFile().getAbsolutePath();
    }

}
