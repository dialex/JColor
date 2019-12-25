package com.diogonunes.jcdp.tests.unit;

import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.impl.WindowsColoredPrinter;
import com.diogonunes.jcdp.tests.helpers.DataGenerator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for WindowsColoredPrinter class.
 *
 * @author Diogo Nunes
 * @version 2.0
 */
public class TestColoredWinPrinter {

    private final String NEWLINE = System.getProperty("line.separator");
    private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeClass
    public static void init() {
        // Redirects standard outputs for testing purposes
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterClass
    public static void reset() {
        // Resets standard outputs to their normal behavior
        System.setOut(null);
        System.setErr(null);
    }

    @After
    public void tearDown() {
        outContent.reset();
        errContent.reset();
    }

    @Test
    public void Constructor_Creation_Default() {
        // ARRANGE

        // ACT
        WindowsColoredPrinter printer = new WindowsColoredPrinter();

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer.isLoggingTimestamps(), equalTo(false));
        assertThat(printer.isLoggingDebug(), equalTo(true));
        assertThat(printer.getLevel(), equalTo(0));
    }

    @Test
    public void Constructor_Creation_PassingBuilderAsParameter() {
        // ARRANGE
        boolean flag = true;
        int number = 1;
        WindowsColoredPrinter.Builder b = new WindowsColoredPrinter.Builder(number, flag);

        // ACT
        WindowsColoredPrinter printer = new WindowsColoredPrinter(b);

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer.getLevel(), equalTo(number));
        assertThat(printer.isLoggingTimestamps(), equalTo(flag));
    }

    @Test
    public void ToString_Description_DisplayName() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter();

        // ACT
        String description = printer.toString();

        // ASSERT
        assertThat(description, containsString(WindowsColoredPrinter.class.getSimpleName()));
    }

    @Test
    public void ToString_Description_DisplayProperties() {
        // ARRANGE
        boolean flag = true;
        int number = 1;
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(number, flag).build();
        String separator = " | ";

        // ACT
        String description = printer.toString();
        String[] descTokens = description.split(Pattern.quote(separator));

        // ASSERT
        assertThat(String.format("Properties are separated by '%1$s'", separator), descTokens.length, greaterThan(0));
        assertThat("Level property is displayed", descTokens[1], containsString("level"));
        assertThat("Level property value is displayed", descTokens[1], containsString(String.valueOf(number)));
        assertThat("Timestamp property is displayed", descTokens[2], containsString("timestamping"));
        assertThat("Timestamp property value is displayed", descTokens[2], containsString(String.valueOf(flag)));
    }

    @Test
    public void ToString_Description_DisplayAnsiProperties() {
        // ARRANGE
        boolean flag = true;
        int number = 1;
        Ansi.Attribute attr = Ansi.Attribute.LIGHT;
        Ansi.FColor fColor = Ansi.FColor.MAGENTA;
        Ansi.BColor bColor = Ansi.BColor.CYAN;
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(number, flag).
                attribute(attr).foreground(fColor).background(bColor).build();
        String separator = " | ";

        // ACT
        String description = printer.toString();
        String[] descTokens = description.split(Pattern.quote(separator));

        // ASSERT
        assertThat("Attribute property is displayed", descTokens[3], containsString("Attribute"));
        assertThat("Attribute property value is displayed", descTokens[3], containsString(attr.name()));
        assertThat("ForegroundColor property is displayed", descTokens[4], containsString("Foreground"));
        assertThat("ForegroundColor property value is displayed", descTokens[4], containsString(fColor.name()));
        assertThat("BackgroundColor property is displayed", descTokens[5], containsString("Background"));
        assertThat("BackgroundColor property value is displayed", descTokens[5], containsString(bColor.name()));
    }

    @Test
    public void Print_Message_DisplayOnSysOut() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createMsg();

        // ACT
        printer.println(msg);

        // ASSERT
        assertThat(outContent.toString(), equalTo(msg + NEWLINE));
    }

    @Test
    public void Print_Message_DisplayTimestamp() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(2, true).build();
        String msg = DataGenerator.createMsg();
        String timestamp = DataGenerator.getCurrentDate(new SimpleDateFormat(DataGenerator.DATE_FORMAT_ISO8601));
        timestamp = timestamp.substring(0, timestamp.lastIndexOf(":")); // ignore seconds

        // ACT
        printer.print(msg);

        // ASSERT
        String output = outContent.toString();
        assertThat("Message is printed", output, containsString(msg));
        assertThat("Message includes timestamp", output, containsString(timestamp));
    }

    @Test
    public void Print_Message_DisplayTimestampAfterEnablingIt() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(2, false).build();
        String msg = DataGenerator.createMsg();
        String timestamp = DataGenerator.getCurrentDate(new SimpleDateFormat(DataGenerator.DATE_FORMAT_ISO8601));
        timestamp = timestamp.substring(0, timestamp.lastIndexOf(":")); // ignore seconds

        // ACT
        printer.print(msg);
        // ASSERT
        assertThat(outContent.toString(), containsString(msg));
        assertThat(outContent.toString(), not(containsString(timestamp)));

        outContent.reset();

        // ACT
        printer.setTimestamping(true);
        printer.print(msg);
        // ASSERT
        assertThat(outContent.toString(), containsString(msg));
        assertThat("After enabling timestamping, message includes it", outContent.toString(), containsString(timestamp));
    }

    @Test
    public void Print_Message_DisplayTimestampWithCustomDateFormat() {
        // ARRANGE
        DateFormat timestampFormat = new SimpleDateFormat("yy.MM.dd");
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(2, true).withFormat(timestampFormat).build();
        String msg = DataGenerator.createMsg();
        String timestamp = DataGenerator.getCurrentDate(timestampFormat);

        // ACT
        printer.print(msg);

        // ASSERT
        assertThat(outContent.toString(), containsString(msg));
        assertThat("Timestamp uses custom format", outContent.toString(), containsString(timestamp));
    }

    @Test
    public void Print_ErrorMessage_DisplayOnSysErr() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createErrorMsg();

        // ACT
        printer.errorPrintln(msg);

        // ASSERT
        assertThat(errContent.toString(), equalTo(msg + NEWLINE));
    }

    @Test
    public void Print_DebugMessage_DisplayOnSysOut() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createMsgWithId(0);

        // ACT
        printer.debugPrintln(msg);

        // ASSERT
        assertThat(outContent.toString(), equalTo(msg + NEWLINE));
    }

    @Test
    public void Print_DebugMessage_DisplayAfterEnablingDebug() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(2, false).build();
        String msg = DataGenerator.createMsgWithId(2);

        // ACT
        printer.setDebugging(false);
        printer.debugPrint(msg, 2);
        printer.setDebugging(true);
        printer.debugPrint(msg, 2);

        // ASSERT
        assertThat("Disabling debug mutes that message type", outContent.toString(), equalTo(msg));
    }

    @Test
    public void Print_DebugMessage_DisplayWithoutLevel() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(1, false).build();
        String msgNoLevel = DataGenerator.createMsg();
        String msgLevelTwo = DataGenerator.createMsgWithId(2);

        // ACT
        printer.debugPrint(msgNoLevel);
        printer.debugPrint(msgLevelTwo, 2);

        // ASSERT
        assertThat(outContent.toString(), containsString(msgNoLevel));
        assertThat(outContent.toString(), not(containsString(msgLevelTwo)));
    }

    @Test
    public void Print_DebugMessage_DisplayIfEnoughLevel() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(2, false).build();
        String msgLevelZero = DataGenerator.createMsgWithId(0);
        String msgLevelOne = DataGenerator.createMsgWithId(1);
        String msgLevelTwo = DataGenerator.createMsgWithId(2);

        // ACT
        printer.debugPrint(msgLevelZero, 0);
        printer.debugPrint(msgLevelOne, 1);
        printer.debugPrint(msgLevelTwo, 2);

        // ASSERT
        assertThat(outContent.toString(), containsString(msgLevelZero));
        assertThat(outContent.toString(), containsString(msgLevelOne));
        assertThat(outContent.toString(), containsString(msgLevelTwo));
    }

    @Test
    public void Print_DebugMessage_DisplayAfterChangingLevel() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(2, false).build();
        String msg = DataGenerator.createMsgWithId(3);

        // ACT
        printer.debugPrint(msg, 3);
        printer.setLevel(3);
        printer.debugPrint(msg, 3);

        // ASSERT
        assertThat("After changing level message is printed", outContent.toString(), equalTo(msg));
    }

    @Test
    public void Print_DebugMessage_IgnoreIfLevelAbove() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(2, false).build();
        String msgLevelTwo = DataGenerator.createMsgWithId(2);
        String msgLevelThree = DataGenerator.createMsgWithId(3);

        // ACT
        printer.debugPrint(msgLevelTwo, 2);
        printer.debugPrint(msgLevelThree, 3);

        // ASSERT
        assertThat(outContent.toString(), containsString(msgLevelTwo));
        assertThat("Ignores messages above current level", outContent.toString(), not(containsString(msgLevelThree)));
    }

    @Test
    public void Print_DebugMessage_IgnoreDebugIfDisabled() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(2, false).build();
        String msg = DataGenerator.createMsg();

        // ACT
        printer.setDebugging(false);
        printer.debugPrint(msg);

        // ASSERT
        assertThat(outContent.toString(), not(containsString(msg)));
    }

    @Test
    public void ColoredPrint_AnsiCode_DelimitCodeWithAnsiTokens() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter();
        Ansi.Attribute attr = Ansi.Attribute.UNDERLINE;
        Ansi.FColor fColor = Ansi.FColor.BLACK;
        Ansi.BColor bColor = Ansi.BColor.YELLOW;

        // ACT
        String ansiCode = printer.generateCode(attr, fColor, bColor);
        String[] codeTokens = ansiCode.split(Pattern.quote(Ansi.SEPARATOR));

        // ASSERT
        assertThat("Code starts with Ansi Prefix", codeTokens[0], containsString(Ansi.PREFIX));
        assertThat("Code ends with Ansi Postfix", codeTokens[codeTokens.length - 1], containsString(Ansi.POSTFIX));
    }

    @Test
    public void ColoredPrint_AnsiCode_GenerateForAllProperties() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter();
        Ansi.Attribute attr = Ansi.Attribute.DARK;
        Ansi.FColor fColor = Ansi.FColor.GREEN;
        Ansi.BColor bColor = Ansi.BColor.BLACK;

        // ACT
        String ansiCode = printer.generateCode(attr, fColor, bColor);
        String[] codeTokens = ansiCode.split(Pattern.quote(Ansi.SEPARATOR));

        // ASSERT
        assertThat("Code contains all attributes", codeTokens.length, equalTo(3));
        assertThat("Code contains attribute", codeTokens[0], containsString(attr.toString()));
        assertThat("Code contains foreground color", codeTokens[1], containsString(fColor.toString()));
        assertThat("Code contains background color", codeTokens[2], containsString(bColor.toString()));
    }

    @Test
    public void ColoredPrint_AnsiCode_GenerateForAttributeOnly() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter();
        Ansi.Attribute attr = Ansi.Attribute.LIGHT;
        Ansi.FColor fColor = Ansi.FColor.NONE;
        Ansi.BColor bColor = Ansi.BColor.NONE;

        // ACT
        String ansiCode = printer.generateCode(attr, fColor, bColor);
        String[] codeTokens = ansiCode.split(Pattern.quote(Ansi.SEPARATOR));

        // ASSERT
        assertThat("Code contains all attributes", codeTokens.length, equalTo(1));
        assertThat("Code contains attribute", codeTokens[0], containsString(attr.toString()));
    }

    @Test
    public void ColoredPrint_AnsiCode_GenerateForForegroundColorOnly() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter();
        Ansi.Attribute attr = Ansi.Attribute.NONE;
        Ansi.FColor fColor = Ansi.FColor.RED;
        Ansi.BColor bColor = Ansi.BColor.NONE;

        // ACT
        String ansiCode = printer.generateCode(attr, fColor, bColor);
        String[] codeTokens = ansiCode.split(Pattern.quote(Ansi.SEPARATOR));

        // ASSERT
        assertThat("Code contains all attributes", codeTokens.length, equalTo(1));
        assertThat("Code contain foreground color", codeTokens[0], containsString(fColor.toString()));
    }

    @Test
    public void ColoredPrint_AnsiCode_GenerateForBackgroundColorOnly() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter();
        Ansi.Attribute attr = Ansi.Attribute.NONE;
        Ansi.FColor fColor = Ansi.FColor.NONE;
        Ansi.BColor bColor = Ansi.BColor.MAGENTA;

        // ACT
        String ansiCode = printer.generateCode(attr, fColor, bColor);
        String[] codeTokens = ansiCode.split(Pattern.quote(Ansi.SEPARATOR));

        // ASSERT
        assertThat("Code contains all attributes", codeTokens.length, equalTo(1));
        assertThat("Code contain background color", codeTokens[0], containsString(bColor.toString()));
    }

    @Test
    public void ColoredPrint_Message_GlobalFormatContainsAnsiCode() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(0, false).
                attribute(Ansi.Attribute.LIGHT).
                foreground(Ansi.FColor.GREEN).
                background(Ansi.BColor.BLACK).build();
        String msg = DataGenerator.createMsg();

        // ACT
        printer.print(msg);

        // ASSERT
        String ansiCode = printer.generateCode();
        assertThat("Message is printed", outContent.toString(), containsString(msg));
        assertThat("Message displays color instead of ansi code", outContent.toString(), not(containsString(ansiCode)));
    }

    @Test
    public void ColoredPrint_ErrorMessage_GlobalFormatContainsAnsiCode() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(0, false).
                attribute(Ansi.Attribute.BOLD).
                foreground(Ansi.FColor.WHITE).
                background(Ansi.BColor.RED).build();
        String msg = DataGenerator.createErrorMsg();

        // ACT
        printer.errorPrint(msg);

        // ASSERT
        String ansiCode = printer.generateCode();
        assertThat("Message is printed", errContent.toString(), containsString(msg));
        assertThat("Message displays color instead of ansi code", errContent.toString(), not(containsString(ansiCode)));
    }

    @Test
    public void ColoredPrint_DebugMessage_GlobalFormatContainsAnsiCode() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(0, false).
                attribute(Ansi.Attribute.LIGHT).
                foreground(Ansi.FColor.CYAN).
                background(Ansi.BColor.BLUE).build();
        String msg = DataGenerator.createMsg();

        // ACT
        printer.debugPrint(msg);

        // ASSERT
        String ansiCode = printer.generateCode();
        assertThat("Message is printed", outContent.toString(), containsString(msg));
        assertThat("Message displays color instead of ansi code", outContent.toString(), not(containsString(ansiCode)));
    }

    @Test
    public void ColoredPrint_Message_SingleMessageFormat() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createMsg();
        Ansi.Attribute attr1 = Ansi.Attribute.LIGHT;
        Ansi.FColor fColor1 = Ansi.FColor.MAGENTA;
        Ansi.BColor bColor1 = Ansi.BColor.CYAN;

        // ACT
        printer.errorPrint(msg);
        printer.print(msg, attr1, fColor1, bColor1);

        // ASSERT
        String ansiCode = printer.generateCode(attr1, fColor1, bColor1);
        assertThat("Message is not formatted with ansi code", errContent.toString(), not(containsString(ansiCode)));
        assertThat("Message is printed", outContent.toString(), containsString(msg));
        assertThat("Message displays color instead of ansi code", outContent.toString(), not(containsString(ansiCode)));
    }

    @Test
    public void ColoredPrint_Message_SingleMessageWithTwoFormats() {
        // ARRANGE
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(0, false).build();
        String separator = DataGenerator.createSeparator();
        String msg = DataGenerator.createMsg();
        Ansi.Attribute attr1 = Ansi.Attribute.LIGHT;
        Ansi.FColor fColor1 = Ansi.FColor.MAGENTA;
        Ansi.BColor bColor1 = Ansi.BColor.CYAN;
        Ansi.Attribute attr2 = Ansi.Attribute.LIGHT;
        Ansi.FColor fColor2 = Ansi.FColor.RED;
        Ansi.BColor bColor2 = Ansi.BColor.BLUE;

        // ACT
        printer.print(msg, attr1, fColor1, bColor1);
        printer.print(separator, attr1, fColor1, bColor1);
        printer.print(msg, attr2, fColor2, bColor2);

        // ASSERT
        String[] messages = outContent.toString().split(Pattern.quote(separator));
        String ansiCode1 = printer.generateCode(attr1, fColor1, bColor1);
        String ansiCode2 = printer.generateCode(attr2, fColor2, bColor2);
        assertThat("Messages were displayed with separator between", messages.length, equalTo(2));
        assertThat("Message displays color instead of ansi code", messages[0], not(containsString(ansiCode1)));
        assertThat("Message displays color instead of ansi code", messages[1], not(containsString(ansiCode2)));
    }

    // TODO refactor: still needed?
    @Test
    public void ColoredPrint_Message_FontIsColoredEvenWithoutBColor() {
        // ARRANGE
        String msg = DataGenerator.createMsg();
        Ansi.FColor fColor = Ansi.FColor.GREEN;
        Ansi.BColor bColor = Ansi.BColor.NONE;
        Ansi.Attribute attr = Ansi.Attribute.NONE;

        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(1, false).build();
        printer.setForegroundColor(fColor);

        // ACT
        printer.println(msg);

        // ASSERT
        String expectedAnsiCode = printer.generateCode(attr, fColor, bColor);
        assertThat("Message displays color instead of ansi code", outContent.toString(), not(containsString(expectedAnsiCode)));
    }

    @Test
    public void ColoredPrint_Message_CustomFormatOverridesPrinterConfigWithTimestampActive() {
        // ARRANGE
        String msg = DataGenerator.createMsg();
        WindowsColoredPrinter printer = new WindowsColoredPrinter.Builder(1, true).build();
        Ansi.Attribute attr = Ansi.Attribute.NONE;
        Ansi.BColor bColor = Ansi.BColor.NONE;

        // ACT
        Ansi.FColor fColor1 = Ansi.FColor.GREEN;
        printer.setForegroundColor(fColor1);
        printer.println(msg);
        // ASSERT
        String expectedAnsiCode = printer.generateCode(attr, fColor1, bColor);
        assertThat("Message displays color instead of ansi code", outContent.toString(), not(containsString(expectedAnsiCode)));
    }
}