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

//    @Test
//    public void GenerateCode_OneAttribute_8bitBackColor() {
//        // ARRANGE
//        Attribute attribute = new BackColor(225);
//        String ansiCodeFor8bitBackColor = "48;5;";
//
//        // ACT
//        String code = generateCode(attribute);
//
//        // ASSERT
//        String expectedCode = PREFIX + attribute + POSTFIX;
//        assertThat(code, equalTo(expectedCode));
//        assertThat(code, containsString(ansiCodeFor8bitBackColor));
//    }

    @Test
    public void Attribute_AnsiCode_TextTrueColor() {
        // ARRANGE
        int r = 255, g = 160, b = 122;
        Attribute attribute = Attribute.TextColor(r, g, b);
        String ansiCodeForRGBForeColor = "38;2;";

        // ACT
        String code = generateCode(attribute);

        // ASSERT
        String expectedCode = ansiCodeForRGBBackColor + r + SEPARATOR + g + SEPARATOR + b;
        assertThat(code, equalTo(expectedCode));
    }

//    @Test
//    public void GenerateCode_OneAttribute_RGBBackColor() {
//        // ARRANGE
//        Attribute attribute = new BackColor(160, 122, 255);
//        String ansiCodeForRGBBackColor = "48;2;";
//
//        // ACT
//        String code = generateCode(attribute);
//
//        // ASSERT
//        String expectedCode = PREFIX + attribute + POSTFIX;
//        assertThat(code, equalTo(expectedCode));
//        assertThat(code, containsString(ansiCodeForRGBBackColor));
//    }
}
