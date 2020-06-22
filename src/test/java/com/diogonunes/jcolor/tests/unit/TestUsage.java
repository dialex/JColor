package com.diogonunes.jcolor.tests.unit;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import org.junit.Test;

import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Ansi.Attribute.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestUsage {

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

}