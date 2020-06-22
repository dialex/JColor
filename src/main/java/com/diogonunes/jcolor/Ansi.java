package com.diogonunes.jcolor;

import static com.diogonunes.jcdp.Constants.NEWLINE;

/**
 * Provides a fluent API to generate
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape sequences</a>
 * by specifying {@link Attribute}s of your text.
 */
public class Ansi {
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
     * Shorthand for the Ansi code that resets to the terminal's default format.
     */
    public static final String RESET = PREFIX + Attribute.CLEAR + POSTFIX;

    public enum Attribute {
        NONE(""),
        CLEAR("0"),
        BOLD("1"),
        SATURATED("1"),     // alias
        DIM("2"),
        DESATURATED("2"),   // alias
        ITALIC("3"),
        UNDERLINE("4"),
        SLOW_BLINK("5"),
        RAPID_BLINK("6"),
        REVERSE("7"),
        HIDDEN("8"),
        STRIKETHROUGH("9"),

        BLACK_TEXT("30"),
        RED_TEXT("31"),
        GREEN_TEXT("32"),
        YELLOW_TEXT("33"),
        BLUE_TEXT("34"),
        MAGENTA_TEXT("35"),
        CYAN_TEXT("36"),
        WHITE_TEXT("37"),

        BLACK_BACK("40"),
        RED_BACK("41"),
        GREEN_BACK("42"),
        YELLOW_BACK("43"),
        BLUE_BACK("44"),
        MAGENTA_BACK("45"),
        CYAN_BACK("46"),
        WHITE_BACK("47"),

        BRIGHT_BLACK_TEXT("90"),
        BRIGHT_RED_TEXT("91"),
        BRIGHT_GREEN_TEXT("92"),
        BRIGHT_YELLOW_TEXT("93"),
        BRIGHT_BLUE_TEXT("94"),
        BRIGHT_MAGENTA_TEXT("95"),
        BRIGHT_CYAN_TEXT("96"),
        BRIGHT_WHITE_TEXT("97"),

        BRIGHT_BLACK_BACK("100"),
        BRIGHT_RED_BACK("101"),
        BRIGHT_GREEN_BACK("102"),
        BRIGHT_YELLOW_BACK("103"),
        BRIGHT_BLUE_BACK("104"),
        BRIGHT_MAGENTA_BACK("105"),
        BRIGHT_CYAN_BACK("106"),
        BRIGHT_WHITE_BACK("107");

        private final String _code; // Ansi escape code

        /**
         * Constructor. Maps an attribute to an Ansi code.
         *
         * @param code Ansi code that represents the attribute.
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
     * @param attributes ANSI attributes to format a text.
     * @return The ANSI code that describes all those attributes together.
     */
    public static String generateCode(Attribute... attributes) {
        StringBuilder builder = new StringBuilder();

        builder.append(PREFIX);
        for (Object option : attributes) {
            String code = option.toString();
            if (code.equals(""))
                continue;
            builder.append(code);
            builder.append(SEPARATOR);
        }
        builder.append(POSTFIX);

        // because code must not end with SEPARATOR
        return builder.toString().replace(SEPARATOR + POSTFIX, POSTFIX);
    }

    /**
     * @param attributes Object containing format attributes.
     * @return The ANSI code that describes all those attributes together.
     */
    public static String generateCode(AnsiFormat attributes) {
        return generateCode(attributes.toArray());
    }

    /**
     * @param text     String to format.
     * @param ansiCode Ansi code to format each message's lines
     * @return The formatted string, ready to be printed.
     */
    public static String colorize(String text, String ansiCode) {
        StringBuilder output = new StringBuilder();
        boolean endsWithLine = text.endsWith(NEWLINE);

        String[] lines = text.split(NEWLINE);
        /*
         * Every formatted line should:
         * 1) start with a code that sets the format
         * 2) end with a code that resets the format
         * This prevents "spilling" the format to other independent prints, which
         * is noticeable when the background is colored. This method ensures those
         * two rules, even when the original message contains newlines.
         */
        for (String line : lines) {
            output.append(ansiCode);
            output.append(line);
            output.append(RESET);
            if (endsWithLine)
                output.append(NEWLINE);
        }
        return output.toString();
    }

    /**
     * @param text       String to format.
     * @param attributes ANSI attributes to format a text.
     * @return The formatted string, ready to be printed.
     */
    public static String colorize(String text, Attribute... attributes) {
        String ansiCode = generateCode(attributes);
        return colorize(text, ansiCode);
    }

    /**
     * @param text       String to format.
     * @param attributes Object containing format attributes.
     * @return The formatted string, ready to be printed.
     */
    public static String colorize(String text, AnsiFormat attributes) {
        return colorize(text, attributes.toArray());
    }

    /**
     * Easter egg. Just an alias of method "colorize".
     *
     * @param text       String to format.
     * @param attributes ANSI attributes to format a text.
     * @return The formatted string, ready to be printed.
     */
    public static String makeItFabulous(String text, Attribute... attributes) {
        return colorize(text, attributes);
    }
}
