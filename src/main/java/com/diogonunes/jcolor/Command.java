package com.diogonunes.jcolor;

public class Command {

    private final String _code;

    /**
     * Constructor. Maps an attribute to an Ansi code.
     *
     * @param code Ansi code that represents the attribute.
     */
    Command(String code) {
        _code = code;
    }

    /**
     * @return Clears the terminal's text, e.g. just like the command-line `clear`.
     */
    public static Command CLEAR_SCREEN() {
        return new Command("2J");
    }

    // Commands

    @Override
    public String toString() {
        return _code;
    }

}
