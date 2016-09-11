package com.diogonunes.jcdp.bw.impl;

import com.diogonunes.jcdp.bw.api.AbstractPrinter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This class is a Terminal implementation of the Printer interface, hence all
 * output is sent to standard output. It implements all abstract methods
 * inherited from the {@link AbstractPrinter} class.
 *
 * @author Diogo Nunes
 * @version 2.0
 */
public class TerminalPrinter extends AbstractPrinter {

    /**
     * Constructor (using defaults): creates a Printer with zero level of debug,
     * timestamping active using format according to ISO 8601.
     */
    public TerminalPrinter() {
        this(new Builder(0, false));
    }

    /**
     * Constructor using builder.
     *
     * @param builder Builder with the desired configurations.
     */
    public TerminalPrinter(Builder builder) {
        setLevel(builder._level);
        setTimestamping(builder._timestampFlag);
        setDateFormat(builder._dateFormat);
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
        private int _level;
        private boolean _timestampFlag;
        // optional parameters, initialized to default values
        private DateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /**
         * The Printer created uses, by default, timestamping format according
         * to ISO 8601.
         *
         * @param level  specifies the maximum level of debug this printer can
         *               print.
         * @param tsFlag true, if you want a timestamp before each message.
         * @see <a href=
         * "http://www.iso.org/iso/catalogue_detail.htm?csnumber=26780">ISO
         * 8601</a>
         */
        public Builder(int level, boolean tsFlag) {
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
        public TerminalPrinter build() {
            return new TerminalPrinter(this);
        }
    }

    // =================================
    // OTHER METHODS (implementations)
    // =================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void printTimestamp() {
        System.out.print(getDateFormatted() + " ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printErrorTimestamp() {
        System.err.print(getDateFormatted() + " ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(Object msg) {
        if (isLoggingTimestamps())
            printTimestamp();
        System.out.print(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(Object msg) {
        if (isLoggingTimestamps())
            printTimestamp();
        System.out.println(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrint(Object msg) {
        if (isLoggingTimestamps())
            printErrorTimestamp();
        System.err.print(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrintln(Object msg) {
        if (isLoggingTimestamps())
            printTimestamp();
        System.err.println(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg) {
        if (isLoggingDebug())
            print(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg, int level) {
        if (isLoggingDebug() && canPrint(level))
            print(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg) {
        println(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg, int level) {
        if (canPrint(level))
            println(msg);
    }

    /**
     * @return The text representation of the Printer.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " | level: " + getLevel() + " | timestamping: " + isLoggingTimestamps();
    }
}