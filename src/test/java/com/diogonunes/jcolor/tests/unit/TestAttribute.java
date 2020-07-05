package com.diogonunes.jcolor.tests.unit;

import com.diogonunes.jcolor.Attribute;
import org.junit.jupiter.api.Test;

import static com.diogonunes.jcolor.Ansi.SEPARATOR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Assert that each Attribute outputs the correct Ansi code.
 */
public class TestAttribute {

    // Effects

    @Test
    public void Attribute_AnsiCode_None() {
        // ARRANGE
        Attribute attribute = Attribute.NONE();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Clear() {
        // ARRANGE
        Attribute attribute = Attribute.CLEAR();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "0";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Bold() {
        // ARRANGE
        Attribute attribute = Attribute.BOLD();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "1";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Saturated() {
        // ARRANGE
        Attribute attribute = Attribute.SATURATED();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "1";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Dim() {
        // ARRANGE
        Attribute attribute = Attribute.DIM();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "2";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Desaturated() {
        // ARRANGE
        Attribute attribute = Attribute.DESATURATED();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "2";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Italic() {
        // ARRANGE
        Attribute attribute = Attribute.ITALIC();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "3";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Underline() {
        // ARRANGE
        Attribute attribute = Attribute.UNDERLINE();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "4";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Slow_blink() {
        // ARRANGE
        Attribute attribute = Attribute.SLOW_BLINK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "5";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Rapid_blink() {
        // ARRANGE
        Attribute attribute = Attribute.RAPID_BLINK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "6";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Reverse() {
        // ARRANGE
        Attribute attribute = Attribute.REVERSE();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "7";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Hidden() {
        // ARRANGE
        Attribute attribute = Attribute.HIDDEN();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "8";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_Strikethrough() {
        // ARRANGE
        Attribute attribute = Attribute.STRIKETHROUGH();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "9";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    // Colors (foreground)

    @Test
    public void Attribute_AnsiCode_BlackText() {
        // ARRANGE
        Attribute attribute = Attribute.BLACK_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "30";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_RedText() {
        // ARRANGE
        Attribute attribute = Attribute.RED_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "31";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_GreenText() {
        // ARRANGE
        Attribute attribute = Attribute.GREEN_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "32";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_YellowText() {
        // ARRANGE
        Attribute attribute = Attribute.YELLOW_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "33";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BlueText() {
        // ARRANGE
        Attribute attribute = Attribute.BLUE_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "34";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_MagentaText() {
        // ARRANGE
        Attribute attribute = Attribute.MAGENTA_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "35";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_CyanText() {
        // ARRANGE
        Attribute attribute = Attribute.CYAN_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "36";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_WhiteText() {
        // ARRANGE
        Attribute attribute = Attribute.WHITE_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "37";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    // Colors (background)

    @Test
    public void Attribute_AnsiCode_BlackBack() {
        // ARRANGE
        Attribute attribute = Attribute.BLACK_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "40";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_RedBack() {
        // ARRANGE
        Attribute attribute = Attribute.RED_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "41";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_GreenBack() {
        // ARRANGE
        Attribute attribute = Attribute.GREEN_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "42";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_YellowBack() {
        // ARRANGE
        Attribute attribute = Attribute.YELLOW_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "43";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BlueBack() {
        // ARRANGE
        Attribute attribute = Attribute.BLUE_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "44";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_MagentaBack() {
        // ARRANGE
        Attribute attribute = Attribute.MAGENTA_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "45";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_CyanBack() {
        // ARRANGE
        Attribute attribute = Attribute.CYAN_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "46";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_WhiteBack() {
        // ARRANGE
        Attribute attribute = Attribute.WHITE_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "47";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    // Bright colors (foreground)

    @Test
    public void Attribute_AnsiCode_BrightBlackText() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_BLACK_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "90";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightRedText() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_RED_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "91";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightGreenText() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_GREEN_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "92";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightYellowText() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_YELLOW_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "93";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightBlueText() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_BLUE_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "94";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightMagentaText() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_MAGENTA_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "95";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightCyanText() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_CYAN_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "96";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightWhiteText() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_WHITE_TEXT();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "97";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    // Bright colors (background)

    @Test
    public void Attribute_AnsiCode_BrightBlackBack() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_BLACK_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "100";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightRedBack() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_RED_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "101";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightGreenBack() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_GREEN_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "102";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightYellowBack() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_YELLOW_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "103";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightBlueBack() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_BLUE_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "104";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightMagentaBack() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_MAGENTA_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "105";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightCyanBack() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_CYAN_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "106";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    @Test
    public void Attribute_AnsiCode_BrightWhiteBack() {
        // ARRANGE
        Attribute attribute = Attribute.BRIGHT_WHITE_BACK();

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedAnsiCode = "107";
        assertThat(code, equalTo(expectedAnsiCode));
    }

    // Complex colors

    @Test
    public void Attribute_AnsiCode_Text8bitColor() {
        // ARRANGE
        int colorNumber = 225;
        Attribute attribute = Attribute.TEXT_COLOR(colorNumber);
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
        Attribute attribute = Attribute.TEXT_COLOR(r, g, b);
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
        Attribute attribute = Attribute.BACK_COLOR(colorNumber);
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
        Attribute attribute = Attribute.BACK_COLOR(r, g, b);
        String ansiCodeForRGBBackColor = "48;2;";

        // ACT
        String code = attribute.toString();

        // ASSERT
        String expectedCode = ansiCodeForRGBBackColor + r + SEPARATOR + g + SEPARATOR + b;
        assertThat(code, equalTo(expectedCode));
    }

}
