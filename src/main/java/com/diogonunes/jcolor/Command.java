package com.diogonunes.jcolor;

/**
 * Abstracts ANSI codes with intuitive names. It maps a command (e.g. CLEAR_SCREEN) with a code.
 */
public class Command {

    private final String _code;

    /**
     * Constructor. Maps a command to an Ansi code.
     *
     * @param code Ansi code that represents the command.
     */
    Command(String code) {
        _code = code;
    }

    /**
     * @return Clears the terminal's text, e.g. just like the command-line `clear`.
     */
    public static Command CLEAR_SCREEN() {
        // Covers https://github.com/dialex/JColor/issues/68
        return new Command("H\\033[2J\"");
    }

    @Override
    public String toString() {
        return _code;
    }
}
