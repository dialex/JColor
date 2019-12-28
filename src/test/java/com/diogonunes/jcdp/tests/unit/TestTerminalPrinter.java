package com.diogonunes.jcdp.tests.unit;

import com.diogonunes.jcdp.bw.impl.TerminalPrinter;
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
 * Tests for TerminalPrinter class.
 *
 * @author Diogo Nunes
 * @version 2.0
 */
public class TestTerminalPrinter {

    private final String newline = System.getProperty("line.separator");
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
        TerminalPrinter printer = new TerminalPrinter();

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
        TerminalPrinter.Builder b = new TerminalPrinter.Builder(number, flag);

        // ACT
        TerminalPrinter printer = new TerminalPrinter(b);

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer.getLevel(), equalTo(number));
        assertThat(printer.isLoggingTimestamps(), equalTo(flag));
    }

    @Test
    public void ToString_Description_DisplayName() {
        // ARRANGE
        TerminalPrinter printer = new TerminalPrinter();

        // ACT
        String description = printer.toString();

        // ASSERT
        assertThat(description, containsString(TerminalPrinter.class.getSimpleName()));
    }

    @Test
    public void ToString_Description_DisplayProperties() {
        // ARRANGE
        boolean flag = true;
        int number = 1;
        TerminalPrinter printer = new TerminalPrinter.Builder(number, flag).build();
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
    public void Print_Message_DisplayOnSysOut() {
        // ARRANGE
        TerminalPrinter printer = new TerminalPrinter.Builder(0, false).build();
        String msg = DataGenerator.createText();

        // ACT
        printer.println(msg);

        // ASSERT
        assertThat(outContent.toString(), equalTo(msg + newline));
    }

    @Test
    public void Print_Message_DisplayTimestamp() {
        // ARRANGE
        TerminalPrinter printer = new TerminalPrinter.Builder(2, true).build();
        String msg = DataGenerator.createText();
        String timestamp = DataGenerator.getCurrentDate(new SimpleDateFormat(DataGenerator.DATE_FORMAT_ISO8601));
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
        TerminalPrinter printer = new TerminalPrinter.Builder(2, false).build();
        String msg = DataGenerator.createText();
        String timestamp = DataGenerator.getCurrentDate(new SimpleDateFormat(DataGenerator.DATE_FORMAT_ISO8601));
        timestamp = timestamp.substring(0, timestamp.lastIndexOf(":")); // ignore seconds

        // ACT
        printer.print(msg);
        printer.setTimestamping(true);
        printer.errorPrint(msg);

        // ASSERT
        assertThat(outContent.toString(), containsString(msg));
        assertThat(outContent.toString(), not(containsString(timestamp)));
        assertThat(errContent.toString(), containsString(msg));
        assertThat("After enabling timestamping, message includes it", errContent.toString(), containsString(timestamp));
    }

    @Test
    public void Print_Message_DisplayTimestampWithCustomDateFormat() {
        // ARRANGE
        DateFormat timestampFormat = new SimpleDateFormat("yy.MM.dd");
        TerminalPrinter printer = new TerminalPrinter.Builder(2, true).withFormat(timestampFormat).build();
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
        TerminalPrinter printer = new TerminalPrinter.Builder(0, false).build();
        String msg = DataGenerator.createText();

        // ACT
        printer.errorPrintln(msg);

        // ASSERT
        assertThat(errContent.toString(), equalTo(msg + newline));
    }

    @Test
    public void Print_DebugMessage_DisplayOnSysOut() {
        // ARRANGE
        TerminalPrinter printer = new TerminalPrinter.Builder(0, false).build();
        String msg = DataGenerator.createTextWithId(0);

        // ACT
        printer.debugPrintln(msg);

        // ASSERT
        assertThat(outContent.toString(), equalTo(msg + newline));
    }

    @Test
    public void Print_DebugMessage_DisplayAfterEnablingDebug() {
        // ARRANGE
        TerminalPrinter printer = new TerminalPrinter.Builder(2, false).build();
        String msg = DataGenerator.createTextWithId(2);

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
        TerminalPrinter printer = new TerminalPrinter.Builder(1, false).build();
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
        TerminalPrinter printer = new TerminalPrinter.Builder(2, false).build();
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
        TerminalPrinter printer = new TerminalPrinter.Builder(2, false).build();
        String msg = DataGenerator.createTextWithId(3);

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
        TerminalPrinter printer = new TerminalPrinter.Builder(2, false).build();
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
        TerminalPrinter printer = new TerminalPrinter.Builder(2, false).build();
        String msg = DataGenerator.createText();

        // ACT
        printer.setDebugging(false);
        printer.debugPrint(msg);

        // ASSERT
        assertThat(outContent.toString(), not(containsString(msg)));
    }
}