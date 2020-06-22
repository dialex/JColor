package com.diogonunes.jcolor.tests.unit;

import com.diogonunes.jcolor.Ansi;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Ansi.Attribute.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for Ansi class.
 */
public class TestAnsi {

    public static final String NEWLINE = System.getProperty("line.separator");

    @Test
    public void Configuration_EscapeCodes() {
        // ARRANGE

        // ACT

        // ASSERT
        char escapeChar = 27; // according to spec: https://en.wikipedia.org/wiki/ANSI_escape_code#Escape_sequences
        assertThat(PREFIX, equalTo(escapeChar + "["));
        assertThat(POSTFIX, equalTo("m"));
        assertThat(SEPARATOR, equalTo(";"));
    }

    @Test
    public void GenerateCode_ZeroAttributes() {
        // ARRANGE
        String msg = "";

        // ACT
        String code = Ansi.generateCode();

        // ASSERT
        String expectedCode = PREFIX + POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test // Covers https://github.com/dialex/JCDP/issues/6
    public void GenerateCode_OneAttribute() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{BOLD};

        // ACT
        String code = Ansi.generateCode(attributes);

        // ASSERT
        String expectedCode = PREFIX + attributes[0] + POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void GenerateCode_MultipleOptions() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{BOLD, CYAN_TEXT};

        // ACT
        String code = Ansi.generateCode(attributes);

        // ASSERT
        String expectedCode = PREFIX + attributes[0] + SEPARATOR + attributes[1] + POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void GenerateCode_MultipleOptionsWithValueNone() {
        // ARRANGE
        Object[] attributes = new Object[]{NONE, NONE, NONE};

        // ACT
        String code = Ansi.generateCode(attributes);

        // ASSERT
        String expectedCode = PREFIX + POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test // Covers https://github.com/dialex/JCDP/issues/6
    public void GenerateCode_SomeOptionsWithValueNone() {
        // ARRANGE
        Object[] attributes = new Object[]{NONE, CYAN_TEXT, NONE};

        // ACT
        String code = Ansi.generateCode(attributes);

        // ASSERT
        String expectedCode = PREFIX + attributes[1] + POSTFIX;
        assertThat(code, equalTo(expectedCode));
        int suffixIndex = code.lastIndexOf(POSTFIX);
        MatcherAssert.assertThat("Code ending in semicolon does not show color", code.charAt(suffixIndex - 1), is(not(';')));
    }

//    @Test
//    public void Colorize_MsgWithoutLine() {
//        // ARRANGE
//        Object[] attributes = new Object[]{BColor.BLUE};
//        String msg = "words without lines";
//
//        // ACT
//        String code = Ansi.generateCode(options);
//        String formattedMsg = Ansi.formatMessage(msg, code);
//
//        // ASSERT
//        assertThat(formattedMsg, startsWith(code));
//        assertThat("Message should clear its format", formattedMsg, endsWith(Ansi.RESET));
//    }
//
//    @Test // Covers https://github.com/dialex/JCDP/issues/38
//    public void Colorize_MsgWithLineEnd() {
//        // ARRANGE
//        Object[] attributes = new Object[]{BColor.BLUE};
//        String msg = createTextLine();
//
//        // ACT
//        String code = Ansi.generateCode(options);
//        String formattedMsg = Ansi.formatMessage(msg, code);
//
//        // ASSERT
//        assertThat(formattedMsg, startsWith(code));
//        assertThat("Format must be cleared before changing line, to avoid format spillage",
//                formattedMsg, endsWith(Ansi.RESET + NEWLINE));
//    }
//
//    @Test // Covers https://github.com/dialex/JCDP/issues/38
//    public void Colorize_MsgMultiplesLines() {
//        // ARRANGE
//        Object[] attributes = new Object[]{BColor.BLUE};
//        String msg1 = createTextWithId(1), msg2 = createTextWithId(2);
//        String fullMsg = msg1 + NEWLINE + msg2 + NEWLINE;
//
//        // ACT
//        String code = Ansi.generateCode(options);
//        String formattedMsg = Ansi.formatMessage(fullMsg, code);
//
//        // ASSERT
//        assertThat(formattedMsg, startsWith(code));
//        assertThat("Middle lines preserve format", formattedMsg, containsString(code + msg2 + Ansi.RESET));
//        assertThat(formattedMsg, endsWith(Ansi.RESET + NEWLINE));
//    }

}
