package com.diogonunes.jcdp.tests.unit;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;
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

import static com.diogonunes.jcdp.Constants.DATE_FORMAT_ISO8601;
import static com.diogonunes.jcdp.Constants.NEWLINE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for ColoredPrinter class.
 */
public class TestColoredPrinter {

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
        ColoredPrinter printer = new ColoredPrinter();

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
        ColoredPrinter.Builder b = new ColoredPrinter.Builder(number, flag);

        // ACT
        ColoredPrinter printer = new ColoredPrinter(b);

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer.getLevel(), equalTo(number));
        assertThat(printer.isLoggingTimestamps(), equalTo(flag));
    }

    @Test
    public void ToString_Description_DisplayName() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(0, false).build();

        // ACT
        String description = printer.toString();

        // ASSERT
        assertThat(description, containsString(ColoredPrinter.class.getSimpleName()));
    }

    @Test
    public void ToString_Description_DisplayProperties() {
        // ARRANGE
        boolean flag = true;
        int number = 1;
        ColoredPrinter printer = new ColoredPrinter.Builder(number, flag).build();
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
        Attribute attr = Attribute.LIGHT;
        FColor fColor = FColor.MAGENTA;
        BColor bColor = BColor.CYAN;
        ColoredPrinter printer = new ColoredPrinter.Builder(number, flag).
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

    // TODO refactor how it asserts formatting
    @Test
    public void Print_Message_DisplayOnSysOut() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createText();

        // ACT
        printer.println(msg);

        // ASSERT
        assertThat(errContent.toString(), equalTo(""));
        assertThat(outContent.toString(), containsString(msg));
        assertThat(outContent.toString(), endsWith(NEWLINE));
    }

    @Test
    public void Print_Message_DisplayTimestamp() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(2, true).build();
        String msg = DataGenerator.createText();
        String timestamp = DataGenerator.getCurrentDate(new SimpleDateFormat(DATE_FORMAT_ISO8601));
        timestamp = timestamp.substring(0, timestamp.lastIndexOf(":")); // ignore seconds

        // ACT
        printer.print(msg);

        // ASSERT
        assertThat("Message is printed", outContent.toString(), containsString(msg));
        assertThat("Message includes timestamp", outContent.toString(), containsString(timestamp));
    }

    @Test
    public void Print_Message_DisplayTimestampAfterEnablingIt() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createText();
        String timestamp = DataGenerator.getCurrentDate(new SimpleDateFormat(DATE_FORMAT_ISO8601));
        timestamp = timestamp.substring(0, timestamp.lastIndexOf(":")); // ignore seconds

        // ACT
        printer.print(msg);
        // ASSERT
        assertThat(outContent.toString(), containsString(msg));
        assertThat(outContent.toString(), not(containsString(timestamp)));

        outContent.reset();

        // ACT
        printer.setTimestamping(true);
        assertThat(printer.isLoggingTimestamps(), is(true));
        printer.print(msg);
        // ASSERT
        assertThat(outContent.toString(), containsString(msg));
        assertThat("After enabling timestamping, message includes it", outContent.toString(), containsString(timestamp));
    }

    @Test
    public void Print_Message_DisplayTimestampWithCustomDateFormat() {
        // ARRANGE
        DateFormat timestampFormat = new SimpleDateFormat("yy.MM.dd");
        ColoredPrinter printer = new ColoredPrinter.Builder(2, true).withFormat(timestampFormat).build();
        String msg = DataGenerator.createText();
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
        ColoredPrinter printer = new ColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createText();

        // ACT
        printer.errorPrintln(msg);

        // ASSERT
        assertThat(outContent.toString(), equalTo(""));
        assertThat(errContent.toString(), containsString(msg));
        assertThat(errContent.toString(), endsWith(NEWLINE));
    }

    // TODO refactor how it asserts formatting
    @Test
    public void Print_DebugMessage_DisplayOnSysOut() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createText();

        // ACT
        printer.debugPrintln(msg);

        // ASSERT
        assertThat(outContent.toString(), containsString(msg));
        assertThat(outContent.toString(), endsWith(NEWLINE));
    }

    // TODO refactor how it asserts formatting
    @Test
    public void Print_DebugMessage_DisplayAfterEnablingDebug() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(2, false).build();
        String msg = DataGenerator.createTextWithId(2);

        // ACT
        printer.setDebugging(false);
        printer.debugPrint(msg, 2);
        printer.setDebugging(true);
        printer.debugPrint(msg, 2);

        // ASSERT
        assertThat("Disabling debug mutes that message type", outContent.toString(), containsString(msg));
    }

    @Test
    public void Print_DebugMessage_DisplayWithoutLevel() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(1, false).build();
        String msgNoLevel = DataGenerator.createText();
        String msgLevelTwo = DataGenerator.createTextWithId(2);

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
        ColoredPrinter printer = new ColoredPrinter.Builder(2, false).build();
        String msgLevelZero = DataGenerator.createTextWithId(0);
        String msgLevelOne = DataGenerator.createTextWithId(1);
        String msgLevelTwo = DataGenerator.createTextWithId(2);

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
        ColoredPrinter printer = new ColoredPrinter.Builder(2, false).build();
        String msgA = DataGenerator.createTextWithId(1);
        String msgB = DataGenerator.createTextWithId(2);

        // ACT
        printer.debugPrint(msgA, 3);
        printer.setLevel(3);
        printer.debugPrint(msgB, 3);

        // ASSERT
        assertThat(outContent.toString(), not(containsString(msgA)));
        assertThat("After changing level message is printed", outContent.toString(), containsString(msgB));
    }

    @Test
    public void Print_DebugMessage_IgnoreIfLevelAbove() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(2, false).build();
        String msgLevelTwo = DataGenerator.createTextWithId(2);
        String msgLevelThree = DataGenerator.createTextWithId(3);

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
        ColoredPrinter printer = new ColoredPrinter.Builder(2, false).build();
        String msg = DataGenerator.createText();

        // ACT
        printer.setDebugging(false);
        printer.debugPrint(msg);

        // ASSERT
        assertThat(outContent.toString(), not(containsString(msg)));
    }

    @Test
    public void ColoredPrint_Message_ContainsAnsiCode() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(0, false).
                attribute(Attribute.LIGHT).
                foreground(FColor.GREEN).
                background(BColor.BLACK).build();
        String msg = DataGenerator.createText();

        // ACT
        printer.print(msg);

        // ASSERT
        String ansiCode = printer.generateCode();
        assertThat("Message is formatted with ansi code", outContent.toString(), startsWith(ansiCode));
        assertThat("Message is printed", outContent.toString(), containsString(msg));
    }

    @Test
    public void ColoredPrint_ErrorMessage_ContainsAnsiCode() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(0, false).
                attribute(Attribute.BOLD).
                foreground(FColor.WHITE).
                background(BColor.RED).build();
        String msg = DataGenerator.createText();

        // ACT
        printer.errorPrint(msg);

        // ASSERT
        String ansiCode = printer.generateCode();
        assertThat("Message is formatted with ansi code", errContent.toString(), startsWith(ansiCode));
        assertThat("Message is printed", errContent.toString(), containsString(msg));
    }

    @Test
    public void ColoredPrint_DebugMessage_ContainsAnsiCode() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(0, false).
                attribute(Attribute.LIGHT).
                foreground(FColor.CYAN).
                background(BColor.BLUE).build();
        String msg = DataGenerator.createText();

        // ACT
        printer.debugPrint(msg);

        // ASSERT
        String ansiCode = printer.generateCode();
        assertThat("Message is formatted with ansi code", outContent.toString(), startsWith(ansiCode));
        assertThat("Message is printed", outContent.toString(), containsString(msg));
    }

    //TODO refactor: make test more precise, using "startsWith" assertion
    @Test
    public void ColoredPrint_Message_SingleMessageFormat() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createText();
        Attribute attr1 = Attribute.LIGHT;
        FColor fColor1 = FColor.MAGENTA;
        BColor bColor1 = BColor.CYAN;

        // ACT
        printer.errorPrint(msg);
        printer.print(msg, attr1, fColor1, bColor1);

        // ASSERT
        String ansiCode = printer.generateCode(attr1, fColor1, bColor1);
        assertThat("Message is not formatted with ansi code", errContent.toString(), not(containsString(ansiCode)));
        assertThat("Message is formatted with ansi code", outContent.toString(), startsWith(ansiCode));
        assertThat("Message is printed", outContent.toString(), containsString(msg));
    }

    //TODO refactor: make test more precise, using "startsWith" assertion
    @Test
    public void ColoredPrint_Message_SingleMessageWithTwoFormats() {
        // ARRANGE
        ColoredPrinter printer = new ColoredPrinter.Builder(0, false).build();
        String separator = DataGenerator.createSeparator();
        String msg = DataGenerator.createText();
        Attribute attr1 = Attribute.LIGHT;
        FColor fColor1 = FColor.MAGENTA;
        BColor bColor1 = BColor.CYAN;
        Attribute attr2 = Attribute.LIGHT;
        FColor fColor2 = FColor.RED;
        BColor bColor2 = BColor.BLUE;

        // ACT
        printer.print(msg, attr1, fColor1, bColor1);
        printer.print(separator, attr1, fColor1, bColor1);
        printer.print(msg, attr2, fColor2, bColor2);

        // ASSERT
        String[] messages = outContent.toString().split(Pattern.quote(separator));
        String ansiCode1 = printer.generateCode(attr1, fColor1, bColor1);
        String ansiCode2 = printer.generateCode(attr2, fColor2, bColor2);
        assertThat("Messages were displayed with separator between", messages.length, equalTo(2));
        assertThat("First message is formatted with ansi code", messages[0], containsString(ansiCode1));
        assertThat("Second message has different ansi code", messages[1], containsString(ansiCode2));
    }

    @Test
    public void ColoredPrint_Message_CustomFormatOverridesPrinterConfigWithTimestampActive() {
        // ARRANGE
        String msg = DataGenerator.createText();
        ColoredPrinter printer = new ColoredPrinter.Builder(1, true).build();

        // ACT
        FColor fColor1 = FColor.GREEN;
        printer.setForegroundColor(fColor1);
        printer.println(msg);

        // ASSERT
        String expectedAnsiCode = printer.generateCode(Attribute.NONE, fColor1, BColor.NONE);
        assertThat("Message is colored with printer's internal config", outContent.toString(), containsString(expectedAnsiCode));
    }

}