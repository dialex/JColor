package com.diogonunes.jcolor;

class BackColorAttribute extends ColorAttribute {

    /**
     * {@inheritDoc}
     */
    BackColorAttribute(int colorNumber) {
        super(colorNumber);
    }

    /**
     * {@inheritDoc}
     */
    BackColorAttribute(int r, int g, int b) {
        super(r, g, b);
    }

    @Override
    protected String getColorAnsiPrefix() {
        String ANSI_8BIT_COLOR_PREFIX = "48;5;";
        String ANSI_TRUE_COLOR_PREFIX = "48;2;";

        return isTrueColor() ? ANSI_TRUE_COLOR_PREFIX : ANSI_8BIT_COLOR_PREFIX;
    }

}
