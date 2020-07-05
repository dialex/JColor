package com.diogonunes.jcolor;

import static java.lang.String.valueOf;

public abstract class ColorAttribute extends Attribute {

    //TODO: refactor into single field, array 1 or 3 elements
    protected final String _simpleColor;
    protected final String[] _rgbColor;

    /**
     * Constructor (8-bit color).
     *
     * @param colorNumber A number (0-255) that represents an 8-bit color.
     */
    ColorAttribute(int colorNumber) {
        if (0 <= colorNumber && colorNumber <= 255) {
            _simpleColor = valueOf(colorNumber);
            _rgbColor = null;
        } else
            throw new IllegalArgumentException("Color must be a number inside range [0-255]. Received: " + colorNumber);
    }

    /**
     * Constructor (true-color).
     *
     * @param r A number (0-255) that represents the red component.
     * @param g A number (0-255) that represents the green component.
     * @param b A number (0-255) that represents the blue component.
     */
    ColorAttribute(int r, int g, int b) {
        if ((0 <= r && r <= 255) && (0 <= g && g <= 255) && (0 <= b && b <= 255)) {
            _rgbColor = new String[]{valueOf(r), valueOf(g), valueOf(b)};
            _simpleColor = "";
        } else
            throw new IllegalArgumentException(
                    String.format("Color components must be a number inside range [0-255]. Received: %d, %d, %d", r, g, b));
    }

    protected boolean isTrueColor() {
        return (_rgbColor != null);
    }

    protected abstract String getColorAnsiPrefix();

    protected String getColorAnsiCode() {
        if (isTrueColor())
            return _rgbColor[0] + Ansi.SEPARATOR + _rgbColor[1] + Ansi.SEPARATOR + _rgbColor[2];
        else
            return _simpleColor;
    }

    @Override
    public String toString() {
        return getColorAnsiPrefix() + getColorAnsiCode();
    }

}
