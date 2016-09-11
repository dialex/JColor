package com.diogonunes.jcdp.bw;

import com.diogonunes.jcdp.bw.api.AbstractPrinter;
import com.diogonunes.jcdp.bw.api.IPrinter;
import com.diogonunes.jcdp.bw.impl.TerminalPrinter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * If you want to create a Printer this is the only class you should use. This
 * is your Printers Factory and it abstracts the creation of a Printer and its
 * real implementation. It offers two types of constructors, one for static and
 * other for dynamic printers. If you use the static constructor you must choose
 * an implementation {@link Printer.Types} offered by the library. If you
 * use the dynamic constructor you must pass as argument an instance of any
 * class that implements {@link IPrinter} interface.
 *
 * @author Diogo Nunes
 * @version 2.0
 */
public class Printer implements IPrinter {

    /**
     * Types of Printer's implementations offered:
     * <ul>
     * <li>TERM for a Terminal Printer;</li>
     * <li>FILE for a File Printer;</li>
     * </ul>
     */
    public enum Types {
        TERM, FILE
    }

    // object with printer's implementation
    private AbstractPrinter _impl;

    // ===========================
    // CONSTRUCTORS and BUILDERS
    // ===========================

    /**
     * Constructor of dynamic printers.
     *
     * @param implementation of {@link IPrinter}
     */
    public Printer(AbstractPrinter implementation) {
        setImpl(implementation);
    }


    /**
     * @param b Builder with the desired configurations for the new printers.
     * @throws IllegalArgumentException if at least one argument is incorrect.
     */
    public Printer(Builder b) throws IllegalArgumentException {
        switch (b._type) {
            case TERM:
                setImpl(new TerminalPrinter.Builder(b._level, b._tsFlag).withFormat(b._dateFormat).build());
                break;
            case FILE:
                throw new IllegalArgumentException("This type of printer isn't supported... yet!");
            default:
                throw new IllegalArgumentException("Unknown type!");
        }
    }

    /**
     * Builder pattern: allows the caller to specify the attributes that it
     * wants to change and keep the default values in the others.
     */
    public static class Builder {
        // required parameters
        private Types _type;
        // optional parameters, initialized to default values
        private int _level = 0;
        private boolean _tsFlag = true;
        private DateFormat _dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        /**
         * Constructor of static printers. The printer returned is one of the
         * implementations offered by the library. By default the Printer
         * created has zero level of debug, timestamping is active with format
         * according to ISO 8601.
         *
         * @param type of implementation wanted, use one of {@link Types}
         */
        public Builder(Types type) {
            _type = type;
        }

        public Builder level(int level) {
            this._level = level;
            return this;
        }

        public Builder timestamping(boolean flag) {
            this._tsFlag = flag;
            return this;
        }

        public Builder withFormat(DateFormat df) {
            this._dateFormat = df;
            return this;
        }

        /**
         * @return a new instance of a Printer.
         * @throws IllegalArgumentException if at least one argument is incorrect.
         */
        public Printer build() throws IllegalArgumentException {
            return new Printer(this);
        }

    }

    // =====================
    // GET and SET METHODS
    // =====================

    private AbstractPrinter getImpl() {
        return _impl;
    }

    private void setImpl(AbstractPrinter impl) {
        _impl = impl;
    }

    // =======================================
    // INTERFACE METHODS call implementation
    // =======================================

    @Override
    public int getLevel() {
        return getImpl().getLevel();
    }

    @Override
    public void setLevel(int level) {
        getImpl().setLevel(level);
    }

    @Override
    public String getDateFormatted() {
        return getImpl().getDateFormatted();
    }

    @Override
    public Date getDate() {
        return getImpl().getDate();
    }

    @Override
    public void printTimestamp() {
        getImpl().printTimestamp();
    }

    @Override
    public void printErrorTimestamp() {
        getImpl().printErrorTimestamp();
    }

    @Override
    public void print(Object msg) {
        getImpl().print(msg);
    }

    @Override
    public void println(Object msg) {
        getImpl().println(msg);
    }

    @Override
    public void errorPrint(Object msg) {
        getImpl().errorPrint(msg);
    }

    @Override
    public void errorPrintln(Object msg) {
        getImpl().errorPrintln(msg);
    }

    @Override
    public void debugPrint(Object msg) {
        getImpl().debugPrint(msg);
    }

    @Override
    public void debugPrint(Object msg, int level) {
        getImpl().debugPrint(msg, level);
    }

    @Override
    public void debugPrintln(Object msg) {
        getImpl().debugPrintln(msg);
    }

    @Override
    public void debugPrintln(Object msg, int level) {
        getImpl().debugPrintln(msg, level);
    }

    @Override
    public String toString() {
        return getImpl().toString();
    }
}