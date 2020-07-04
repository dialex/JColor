package com.diogonunes.jcolor;

/**
 * Provides a fluent API to generate
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape sequences</a>
 * by specifying {@link Attribute}s of your text.
 */
public class Ansi {

    private static final char ESC = 27; // Escape character used to start an ANSI code
    private static final String NEWLINE = System.getProperty("line.separator");

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
    public static final String RESET = PREFIX + Attribute.CLEAR() + POSTFIX;

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
