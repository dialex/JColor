package com.diogonunes.jcolor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstracts an Array of {@link Attribute}s.
 * Use it if you find this more readable than Attribute[].
 */
public class AnsiFormat {

    // Starts with capacity=2 because that's how many attributes are used on average
    private final ArrayList<Attribute> _attributes = new ArrayList<>(2);

    /**
     * @param attributes All ANSI attributes to format a text.
     */
    public AnsiFormat(Attribute... attributes) {
        _attributes.addAll(Arrays.asList(attributes));
    }

    /**
     * @param text String to format.
     * @return The formatted string, ready to be printed.
     */
    public String format(String text) {
        return Ansi.colorize(text, this.toArray());
    }

    protected Attribute[] toArray() {
        return _attributes.toArray(new Attribute[0]);
    }
}
