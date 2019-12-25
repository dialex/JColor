package com.diogonunes.jcdp.color.api;

/**
 * This class stores enums used to generate a Ansi escape code. There is one
 * enum for each component of the print format: Attribute, Foreground Color,
 * Background Color.
 *
 * @author Diogo Nunes
 * @version 1.2
 * @see <a href="http://ascii-table.com/ansi-escape-sequences.php">Ansi escape
 * codes</a>
 */
public class Ansi {

    private static final String NEWLINE = System.getProperty("line.separator");
    private static final char ESC = 27; // Escape character used to start an ANSI code

    /**
     * Every Ansi escape code begins with this PREFIX.
     */
    public static final String PREFIX = ESC + "[";
    /**
     * Two options must be separated by this SEPARATOR.
     */
    public static final String SEPARATOR = ";";
    /**
     * Every Ansi escape code must end with this POSTFIX.
     */
    public static final String POSTFIX = "m";
    /**
     * Shorthand for the Ansi code that clears the current format and resets to the console's default.
     */
    public static final String RESET = PREFIX + Attribute.CLEAR + POSTFIX;

    /**
     * Enumeration of each Ansi code for Foreground Color.
     */
    public enum FColor {

        BLACK("30"), RED("31"), GREEN("32"), YELLOW("33"), BLUE("34"), MAGENTA("35"), CYAN("36"), WHITE("37"), NONE("");

        private final String _code; // Ansi escape code

        /**
         * Enum's constructor. Associates a code to a Foreground Color.
         *
         * @param code to associate
         */
        FColor(String code) {
            _code = code;
        }

        /**
         * @return Ansi escape code for that Foreground Color.
         */
        public String getCode() {
            return _code;
        }

        /**
         * @return The text representation of the enum (its code).
         */
        @Override
        public String toString() {
            return getCode();
        }
    }

    /**
     * Enumeration of each Ansi code for Background Color.
     */
    public enum BColor {

        BLACK("40"), RED("41"), GREEN("42"), YELLOW("43"), BLUE("44"), MAGENTA("45"), CYAN("46"), WHITE("47"), NONE("");

        private final String _code; // Ansi escape code

        /**
         * Enum's constructor. Associates a code to a Background Color.
         *
         * @param code to associate
         */
        BColor(String code) {
            _code = code;
        }

        /**
         * @return Ansi escape code for that Foreground Color.
         */
        public String getCode() {
            return _code;
        }

        /**
         * @return The text representation of the enum (its code).
         */
        @Override
        public String toString() {
            return getCode();
        }
    }

    /**
     * Enumeration of each Ansi code for Attribute.
     */
    public enum Attribute {

        CLEAR("0"), BOLD("1"), LIGHT("1"), DARK("2"), UNDERLINE("4"), REVERSE("7"), HIDDEN("8"), NONE("");

        private final String _code; // Ansi escape code

        /**
         * Enum's constructor. Associates a code to a Attribute.
         *
         * @param code to associate
         */
        Attribute(String code) {
            _code = code;
        }

        /**
         * @return Ansi escape code for that attribute.
         */
        public String getCode() {
            return _code;
        }

        /**
         * @return The text representation of the enum (its code).
         */
        @Override
        public String toString() {
            return getCode();
        }
    }

    /**
     * @param options ANSI options like background color, font attributes, etc.
     * @return the ANSI code used to tell the terminal how to display a message.
     */
    public static String generateCode(Object... options) {
        StringBuilder builder = new StringBuilder();

        builder.append(Ansi.PREFIX);
        for (Object option : options) {
            String code = option.toString();
            if (code.equals(""))
                continue;
            builder.append(code);
            builder.append(Ansi.SEPARATOR);
        }
        builder.append(Ansi.POSTFIX);

        // because code must not end with SEPARATOR
        return builder.toString().replace(SEPARATOR + POSTFIX, POSTFIX);
    }

    /**
     * Every formatted line should:
     * 1) start with a code that sets the format;
     * 2) end with a code that resets the format.
     * This prevents "spilling" the format to other independent prints, which
     * is noticeable when the background is colored. This method ensures those
     * two rules, even when the original message contains newlines in itself.
     *
     * @param msg        Message to format, while preventing spillages
     * @param ansiFormat Ansi format of each msg lines
     * @return The formatted message, without formatting side-effects
     */
    public static String formatMessage(String msg, String ansiFormat) {
        StringBuilder output = new StringBuilder();
        boolean endsWithLine = msg.endsWith(NEWLINE);

        String[] lines = msg.split(NEWLINE);
        for (String line : lines) {
            output.append(ansiFormat);
            output.append(line);
            output.append(Ansi.RESET);
            if (endsWithLine)
                output.append(NEWLINE);
        }
        return output.toString();
    }
}