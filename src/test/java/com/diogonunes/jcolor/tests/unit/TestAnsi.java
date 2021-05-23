package com.diogonunes.jcolor.tests.unit;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.Command;
import org.junit.jupiter.api.Test;

import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Attribute.*;
import static com.diogonunes.jcolor.tests.unit.DataGenerator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
        Attribute[] attributes = new Attribute[]{};

        // ACT
        String code = Ansi.generateCode(attributes);

        // ASSERT
        String expectedCode = PREFIX + POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test // Covers https://github.com/dialex/JColor/issues/56
    public void GenerateCode_OneCommand() {
        // ARRANGE
        Command command = Command.CLEAR_SCREEN();

        // ACT
        String code = Ansi.generateCode(command);

        // ASSERT
        String expectedCode = PREFIX + command;
        assertThat(code, equalTo(expectedCode));
    }

    @Test // Covers https://github.com/dialex/JColor/issues/6
    public void GenerateCode_OneAttribute_Simple() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{STRIKETHROUGH()};

        // ACT
        String code = Ansi.generateCode(attributes);

        // ASSERT
        String expectedCode = PREFIX + attributes[0] + POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void GenerateCode_OneAttribute_Color() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{TEXT_COLOR(225)};

        // ACT
        String code = Ansi.generateCode(attributes);

        // ASSERT
        String expectedCode = PREFIX + attributes[0] + POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void GenerateCode_MultipleAttributes_HandlesArray() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{DIM(), CYAN_TEXT()};

        // ACT
        String code = Ansi.generateCode(attributes);

        // ASSERT
        String expectedCode = PREFIX + attributes[0] + SEPARATOR + attributes[1] + POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void GenerateCode_MultipleAttributes_HandlesAnsiFormat() {
        // ARRANGE
        AnsiFormat attributes = new AnsiFormat(DIM(), CYAN_TEXT());

        // ACT
        String code = Ansi.generateCode(attributes);

        // ASSERT
        String expectedCode = PREFIX + DIM() + SEPARATOR + CYAN_TEXT() + POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void GenerateCode_MultipleAttributes_HandlesMultipleParams() {
        // ARRANGE
        Attribute firstAttribute = UNDERLINE();
        Attribute secondAttribute = GREEN_TEXT();

        // ACT
        String code = Ansi.generateCode(firstAttribute, secondAttribute);

        // ASSERT
        String expectedCode = PREFIX + firstAttribute + SEPARATOR + secondAttribute + POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void GenerateCode_MultipleAttributes_HandlesAttributesWithoutCode() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{NONE(), NONE(), NONE()};

        // ACT
        String code = Ansi.generateCode(attributes);

        // ASSERT
        String expectedCode = PREFIX + POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test // Covers https://github.com/dialex/JColor/issues/6
    public void GenerateCode_MultiplesAttributes_HandlesAttributesMixedWithNone() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{NONE(), BLUE_TEXT(), NONE()};

        // ACT
        String code = Ansi.generateCode(attributes);

        // ASSERT
        String expectedCode = PREFIX + attributes[1] + POSTFIX;
        assertThat(code, equalTo(expectedCode));
        int suffixIndex = code.lastIndexOf(POSTFIX);
        assertThat("Code ending in semicolon does not show color", code.charAt(suffixIndex - 1), is(not(';')));
    }

    @Test
    public void Colorize_TextWithoutLines() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{YELLOW_BACK()};
        String text = createText();

        // ACT
        String formattedText = Ansi.colorize(text, attributes);
        //System.out.println(formattedText);

        // ASSERT
        String expectedCode = Ansi.generateCode(attributes);
        assertThat(formattedText, startsWith(expectedCode));
        assertThat("Message should clear its format", formattedText, endsWith(Ansi.RESET));
    }

    @Test // Covers https://github.com/dialex/JColor/issues/56
    public void Colorize_SingleCommand() {
        // ARRANGE
        Command command = Command.CLEAR_SCREEN();
        String text = createTextLine();

        // ACT
        String formattedCommand = Ansi.colorize(command);
        //System.out.println(formattedText);

        // ASSERT
        String expectedCode = Ansi.generateCode(command);
        assertThat(formattedCommand, equalTo(expectedCode));
    }

    @Test // Covers https://github.com/dialex/JColor/issues/38
    public void Colorize_TextWithSingleLine() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{GREEN_BACK()};
        String text = createTextLine();

        // ACT
        String formattedText = Ansi.colorize(text, attributes);
        //System.out.println(formattedText);

        // ASSERT
        String expectedCode = Ansi.generateCode(attributes);
        assertThat("Format must be cleared before changing line, to avoid format spillage",
                formattedText, endsWith(Ansi.RESET));
    }

    @Test // Covers https://github.com/dialex/JColor/issues/38
    public void Colorize_TextWithMultiplesLines() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{RED_BACK()};
        String text1 = createTextWithId(1), text2 = createTextWithId(2);
        String fullText = text1 + NEWLINE + text2 + NEWLINE;

        // ACT
        String formattedText = Ansi.colorize(fullText, attributes);
        //System.out.println(formattedText);

        // ASSERT
        String expectedCode = Ansi.generateCode(attributes);
        assertThat("Middle lines preserve format", formattedText, containsString(expectedCode + text2 + Ansi.RESET));
        assertThat(formattedText, endsWith(Ansi.RESET));
    }

    @Test // Covers https://github.com/dialex/JColor/issues/51
    public void Colorize_TextWithMultipleEmptyLines() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{(CYAN_BACK())};
        String emptyLines = NEWLINE + NEWLINE + NEWLINE;
        String fullText = createText() + emptyLines;

        // ACT
        String formattedText = Ansi.colorize(fullText, attributes);
        //System.out.println(formattedText);

        // ASSERT

        assertThat("Empty lines are not deleted", countLines(formattedText), equalTo(countLines(emptyLines)));
        assertThat(formattedText, endsWith(Ansi.RESET));
    }

    @Test // Covers https://github.com/dialex/JColor/issues/51
    public void Colorize_TextWithMiddleEmptyLines() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{(CYAN_BACK())};
        String text1 = createTextWithId(1), text2 = createTextWithId(2);
        String fullText = text1 + NEWLINE + NEWLINE + text2;

        // ACT
        String formattedText = Ansi.colorize(fullText, attributes);
        System.out.println(formattedText);

        // ASSERT
        assertThat("Middle empty lines are not deleted", countLines(formattedText), equalTo(countLines(fullText)));
        assertThat(formattedText, endsWith(Ansi.RESET));
    }

    @Test
    public void Colorize_ConflictingAttributes_UsesTheLast() {
        // ARRANGE
        Attribute[] attributes = new Attribute[]{BLACK_BACK(), WHITE_BACK(), BLUE_BACK()};
        String text = "This text will have a blue back";

        // ACT
        String formattedText = Ansi.colorize(text, attributes);
        //System.out.println(formattedText);

        // ASSERT
        String expectedCode = Ansi.generateCode(attributes);
        assertThat(formattedText, startsWith(expectedCode));
        assertThat("Message should clear its format", formattedText, endsWith(Ansi.RESET));
    }
}
