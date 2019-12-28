package com.diogonunes.jcdp.color;

import com.diogonunes.jcdp.bw.api.IPrinter;
import com.diogonunes.jcdp.color.api.AbstractColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;
import com.diogonunes.jcdp.color.api.IColoredPrinter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * If you want to create a ColoredPrinter this is the class you should use. This class lets
 * you define a format once and print all messages using that format.
 * Check the {@link IColoredPrinter} for the full API. This class also implements all abstract
 * methods inherited from the {@link AbstractColoredPrinter} class.
 */
public class ColoredPrinter extends AbstractColoredPrinter {

    //TODO refactor: move shared constants to shared Constant class
    private final String NEWLINE = System.getProperty("line.separator");

    /**
     * Constructor (using defaults): creates a Colored Printer with no format,
     * zero level of debug and timestamping active according to ISO 8601.
     */
    public ColoredPrinter() {
        this(new Builder(0, false));
    }

    /**
     * Constructor using builder.
     *
     * @param builder Builder with the desired configurations.
     */
    public ColoredPrinter(Builder builder) {
        setLevel(builder._level);
        setTimestamping(builder._timestampFlag);
        setDateFormat(builder._dateFormat);
        setAttribute(builder._attribute);
        setForegroundColor(builder._foregroundColor);
        setBackgroundColor(builder._backgroundColor);
    }

    // =========
    // BUILDER
    // =========

    /**
     * Builder pattern: allows the caller to specify the attributes that it
     * wants to change and keep the default values in the others.
     */
    public static class Builder {
        // required parameters
        private int _level;
        private boolean _timestampFlag;
        // optional parameters - initialized to default values
        private Attribute _attribute = Attribute.NONE;
        private FColor _foregroundColor = FColor.NONE;
        private BColor _backgroundColor = BColor.NONE;
        private DateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /**
         * The Colored Printer created, by default, has no format. So it's just
         * like a usual Printer {@link IPrinter}.
         *
         * @param level  specifies the maximum level of debug this printer can
         *               print.
         * @param tsFlag true, if you want a timestamp before each message.
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
         * @param attr specifies the attribute component of the printing format.
         * @return the builder.
         */
        public Builder attribute(Attribute attr) {
            this._attribute = attr;
            return this;
        }

        /**
         * @param fg specifies the foreground color of the printing format.
         * @return the builder.
         */
        public Builder foreground(FColor fg) {
            this._foregroundColor = fg;
            return this;
        }

        /**
         * @param bg specifies the background color of the printing format.
         * @return the builder.
         */
        public Builder background(BColor bg) {
            this._backgroundColor = bg;
            return this;
        }

        /**
         * @return a new instance of ColoredPrinterNIX.
         */
        public ColoredPrinter build() {
            return new ColoredPrinter(this);
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
        System.out.print(generateCode() + getDateFormatted() + " ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printErrorTimestamp() {
        System.err.print(generateCode() + getDateFormatted() + " ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(Object msg) {
        formattedPrint(msg, generateCode(), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(Object msg, Attribute attr, FColor fg, BColor bg) {
        formattedPrint(msg, generateCode(attr, fg, bg), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(Object msg) {
        formattedPrint(msg, generateCode(), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(Object msg, Attribute attr, FColor fg, BColor bg) {
        formattedPrint(msg, generateCode(attr, fg, bg), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrint(Object msg) {
        if (isLoggingTimestamps()) {
            printErrorTimestamp();
        } else {
            System.err.print(generateCode());
        }
        System.err.print(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrint(Object msg, Attribute attr, FColor fg, BColor bg) {
        if (isLoggingTimestamps()) {
            printTimestamp();
        } else {
            System.out.print(generateCode(attr, fg, bg));
        }
        System.err.print(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrintln(Object msg) {
        if (isLoggingTimestamps()) {
            printTimestamp();
        } else {
            System.out.print(generateCode());
        }
        System.err.println(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrintln(Object msg, Attribute attr, FColor fg, BColor bg) {
        if (isLoggingTimestamps()) {
            printTimestamp();
        } else {
            System.out.print(generateCode(attr, fg, bg));
        }
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
    public void debugPrint(Object msg, Attribute attr, FColor fg, BColor bg) {
        if (isLoggingDebug())
            print(msg, attr, fg, bg);
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
    public void debugPrint(Object msg, int level, Attribute attr, FColor fg, BColor bg) {
        if (canPrint(level))
            print(msg, attr, fg, bg);
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
    public void debugPrintln(Object msg, Attribute attr, FColor fg, BColor bg) {
        println(msg, attr, fg, bg);
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
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg, int level, Attribute attr, FColor fg, BColor bg) {
        if (canPrint(level))
            println(msg, attr, fg, bg);
    }

    /**
     * @return The text representation of the Printer.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " | level: " + getLevel() + " | timestamping: "
                + isLoggingTimestamps() + " | Attribute: " + getAttribute().name()
                + " | Foreground color: " + getForegroundColor().name() + " | Background color: "
                + getBackgroundColor().name();
    }

    private void formattedPrint(Object msg, String ansiFormatCode, boolean appendNewline) {
        StringBuilder output = new StringBuilder();
        output.append(isLoggingTimestamps() ? getDateFormatted() + " " : "");
        output.append(msg);
        output.append(appendNewline ? NEWLINE : "");

        String formattedMsg = Ansi.formatMessage(output.toString(), ansiFormatCode);
        System.out.print(formattedMsg);
    }
}