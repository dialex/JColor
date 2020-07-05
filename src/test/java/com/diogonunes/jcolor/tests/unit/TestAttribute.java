package com.diogonunes.jcolor.tests.unit;

import com.diogonunes.jcolor.Attribute;
import org.junit.jupiter.api.Test;

import static com.diogonunes.jcolor.Ansi.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Assert that each Attribute outputs the correct Ansi code.
 */
public class TestAttribute {

    @Test
    public void Attribute_AnsiCode_Text8bitColor() {
        // ARRANGE
        int colorNumber = 225;
        Attribute attribute = Attribute.TextColor(colorNumber);
        String ansiCodeFor8bitForeColor = "38;5;";

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedCode = ansiCodeFor8bitForeColor + colorNumber;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void Attribute_AnsiCode_TextTrueColor() {
        // ARRANGE
        int r = 255, g = 160, b = 122;
        Attribute attribute = Attribute.TextColor(r, g, b);
        String ansiCodeForRGBForeColor = "38;2;";

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedCode = ansiCodeForRGBForeColor + r + SEPARATOR + g + SEPARATOR + b;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void Attribute_AnsiCode_Back8bitColor() {
        // ARRANGE
        int colorNumber = 225;
        Attribute attribute = Attribute.BackColor(colorNumber);
        String ansiCodeFor8bitBackColor = "48;5;";

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedCode = ansiCodeFor8bitBackColor + colorNumber;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void Attribute_AnsiCode_BackTrueColor() {
        // ARRANGE
        int r = 160, g = 122, b = 255;
        Attribute attribute = Attribute.BackColor(r, g, b);
        String ansiCodeForRGBBackColor = "48;2;";

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedCode = ansiCodeForRGBBackColor + r + SEPARATOR + g + SEPARATOR + b;
        assertThat(code, equalTo(expectedCode));
    }

}
