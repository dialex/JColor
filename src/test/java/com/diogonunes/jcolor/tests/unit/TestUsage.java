package com.diogonunes.jcolor.tests.unit;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import org.junit.jupiter.api.Test;

import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Ansi.Attribute.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestUsage {

    @Test
    public void CanFormatWithOneAttribute() {
        // ARRANGE
        AnsiFormat fInfo = new AnsiFormat(CYAN_BACK);

        // ACT
        String formattedText = fInfo.format("This text will have a cyan back");
        //System.out.println(formattedText);

        // ASSERT
        String expectedCode = Ansi.generateCode(fInfo);
        assertThat(formattedText, startsWith(expectedCode));
        assertThat("Message should clear its format", formattedText, endsWith(Ansi.RESET));
    }

    @Test
    public void CanFormatWithMultipleAttributes() {
        // ARRANGE
        AnsiFormat fWarning = new AnsiFormat(RED_TEXT, YELLOW_BACK, BOLD);

        // ACT
        String formattedText = fWarning.format("This bold text will be red on yellow");
        //System.out.println(formattedText);

        // ASSERT
        String expectedCode = Ansi.generateCode(fWarning);
        assertThat(formattedText, startsWith(expectedCode));
        assertThat("Message should clear its format", formattedText, endsWith(Ansi.RESET));
    }

    @Test
    public void CanFormatInline() {
        // ARRANGE

        // ACT
        String formattedText = colorize("This text will be yellow on magenta", YELLOW_TEXT, MAGENTA_BACK);
        //System.out.println(formattedText);

        // ASSERT
        String expectedCode = Ansi.generateCode(YELLOW_TEXT, MAGENTA_BACK);
        assertThat(formattedText, startsWith(expectedCode));
        assertThat("Message should clear its format", formattedText, endsWith(Ansi.RESET));
    }

    @Test
    public void CanMakeItFabulous() {
        // ARRANGE
        String text = "This text will be magenta";

        // ACT
        String formattedText = makeItFabulous(text, MAGENTA_TEXT);

        // ASSERT
        assertThat(formattedText, equalTo(colorize(text, MAGENTA_TEXT)));
    }

    @Test
    public void CanUseAnsiFormatOrArray() {
        // ARRANGE
        AnsiFormat formatNotation = new AnsiFormat(BLACK_TEXT, BLACK_BACK);
        Attribute[] arrayNotation = new Attribute[]{BLACK_TEXT, BLACK_BACK};
        String text = "This text will be black on black";

        // ACT
        String formatNotationOutput = formatNotation.format(text);
        String arrayNotationOutput = Ansi.colorize(text, arrayNotation);

        // ASSERT
        assertThat(formatNotationOutput, equalTo(arrayNotationOutput));
        assertThat(formatNotationOutput.compareTo(arrayNotationOutput), equalTo(0));
    }

}