package com.diogonunes.jcdp.tests.unit;

import com.diogonunes.jcdp.color.api.Ansi;
import org.junit.Test;

import static com.diogonunes.jcdp.tests.helpers.DataGenerator.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for Ansi class.
 */
public class TestAnsi {

    @Test
    public void Configuration_EscapeCode() {
        // ARRANGE

        // ACT

        // ASSERT
        char escapeChar = 27; // according to spec: https://en.wikipedia.org/wiki/ANSI_escape_code#Escape_sequences
        assertThat(Ansi.PREFIX, equalTo(escapeChar + "["));
    }

    @Test
    public void GenerateCode_ZeroOptions() {
        // ARRANGE
        // ACT
        String code = Ansi.generateCode();

        // ASSERT
        String expectedCode = Ansi.PREFIX + Ansi.POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test // Addresses https://github.com/dialex/JCDP/issues/6
    public void GenerateCode_OneOption() {
        // ARRANGE
        Object[] options = new Object[]{Ansi.Attribute.BOLD};

        // ACT
        String code = Ansi.generateCode(options);

        // ASSERT
        String expectedCode = Ansi.PREFIX + options[0] + Ansi.POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void GenerateCode_MultipleOptions() {
        // ARRANGE
        Object[] options = new Object[]{Ansi.Attribute.BOLD, Ansi.FColor.CYAN};

        // ACT
        String code = Ansi.generateCode(options);

        // ASSERT
        String expectedCode = Ansi.PREFIX + options[0] + Ansi.SEPARATOR + options[1] + Ansi.POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void GenerateCode_MultipleOptionsWithValueNone() {
        // ARRANGE
        Object[] options = new Object[]{Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.NONE};

        // ACT
        String code = Ansi.generateCode(options);

        // ASSERT
        String expectedCode = Ansi.PREFIX + Ansi.POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test // Addresses https://github.com/dialex/JCDP/issues/6
    public void GenerateCode_SomeOptionsWithValueNone() {
        // ARRANGE
        Object[] options = new Object[]{Ansi.Attribute.NONE, Ansi.FColor.CYAN, Ansi.BColor.NONE};

        // ACT
        String code = Ansi.generateCode(options);

        // ASSERT
        String expectedCode = Ansi.PREFIX + options[1] + Ansi.POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void FormatMessage_MsgWithoutLine() {
        // ARRANGE
        Object[] options = new Object[]{Ansi.BColor.BLUE};
        String msg = "words without lines";

        // ACT
        String code = Ansi.generateCode(options);
        String formattedMsg = Ansi.formatMessage(msg, code);

        // ASSERT
        assertThat(formattedMsg, startsWith(code));
        assertThat("Message should clear its format", formattedMsg, endsWith(Ansi.RESET));
    }

    @Test // Addresses https://github.com/dialex/JCDP/issues/38
    public void FormatMessage_MsgWithLineEnd() {
        // ARRANGE
        Object[] options = new Object[]{Ansi.BColor.BLUE};
        String msg = createTextLine();

        // ACT
        String code = Ansi.generateCode(options);
        String formattedMsg = Ansi.formatMessage(msg, code);

        // ASSERT
        assertThat(formattedMsg, startsWith(code));
        assertThat("Format must be cleared before changing line, to avoid format spillage",
                formattedMsg, endsWith(Ansi.RESET + NEWLINE));
    }

    @Test // Addresses https://github.com/dialex/JCDP/issues/38
    public void FormatMessage_MsgMultiplesLines() {
        // ARRANGE
        Object[] options = new Object[]{Ansi.BColor.BLUE};
        String msg1 = createTextWithId(1), msg2 = createTextWithId(2);
        String fullMsg = msg1 + NEWLINE + msg2 + NEWLINE;

        // ACT
        String code = Ansi.generateCode(options);
        String formattedMsg = Ansi.formatMessage(fullMsg, code);

        // ASSERT
        assertThat(formattedMsg, startsWith(code));
        assertThat("Middle lines preserve format", formattedMsg, containsString(code + msg2 + Ansi.RESET));
        assertThat(formattedMsg, endsWith(Ansi.RESET + NEWLINE));
    }
}
