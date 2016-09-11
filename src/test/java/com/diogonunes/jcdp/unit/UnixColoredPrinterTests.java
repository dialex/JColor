package com.diogonunes.jcdp.unit;

import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.impl.UnixColoredPrinter;
import helpers.DataGenerator;
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
 * Tests for UnixColoredPrinter class.
 *
 * @author Diogo Nunes
 * @version 2.0
 */
public class UnixColoredPrinterTests {

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
        UnixColoredPrinter printer = new UnixColoredPrinter();

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
        UnixColoredPrinter.Builder b = new UnixColoredPrinter.Builder(number, flag);

        // ACT
        UnixColoredPrinter printer = new UnixColoredPrinter(b);

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer.getLevel(), equalTo(number));
        assertThat(printer.isLoggingTimestamps(), equalTo(flag));
    }

    @Test
    public void ToString_Description_DisplayName() {
        // ARRANGE
        UnixColoredPrinter printer = new UnixColoredPrinter();

        // ACT
        String description = printer.toString();

        // ASSERT
        assertThat(description, containsString(UnixColoredPrinter.class.getSimpleName()));
    }

    @Test
    public void ToString_Description_DisplayProperties() {
        // ARRANGE
        boolean flag = true;
        int number = 1;
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(number, flag).build();
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
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createMsg();

        // ACT
        printer.print(msg);

        // ASSERT
        assertThat(outContent.toString(), equalTo(msg));
    }

    @Test
    public void Print_Message_DisplayTimestamp() {
        // ARRANGE
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(2, true).build();
        String msg = DataGenerator.createMsg();
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
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(2, false).build();
        String msg = DataGenerator.createMsg();
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
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(2, true).withFormat(timestampFormat).build();
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
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createErrorMsg();

        // ACT
        printer.errorPrint(msg);

        // ASSERT
        assertThat(errContent.toString(), equalTo(msg));
    }

    @Test
    public void Print_DebugMessage_DisplayOnSysOut() {
        // ARRANGE
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(0, false).build();
        String msg = DataGenerator.createMsgWithId(0);

        // ACT
        printer.debugPrint(msg);

        // ASSERT
        assertThat(outContent.toString(), equalTo(msg));
    }

    @Test
    public void Print_DebugMessage_DisplayAfterEnablingDebug() {
        // ARRANGE
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(2, false).build();
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
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(1, false).build();
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
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(2, false).build();
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
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(2, false).build();
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
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(2, false).build();
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
        UnixColoredPrinter printer = new UnixColoredPrinter.Builder(2, false).build();
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
        UnixColoredPrinter printer = new UnixColoredPrinter();
        Ansi.Attribute attr = Ansi.Attribute.UNDERLINE;
        Ansi.FColor fColor = Ansi.FColor.BLACK;
        Ansi.BColor bColor = Ansi.BColor.YELLOW;

        // ACT
        String ansiCode = printer.generateCode(attr, fColor, bColor);
        String[] codeTokens = ansiCode.split(Pattern.quote(Ansi.SEPARATOR));

        // ASSERT
        assertThat("Code starts with Ansi Prefix", codeTokens[0], containsString(Ansi.PREFIX));
        assertThat("Code ends with Ansi Postfix", codeTokens[codeTokens.length-1], containsString(Ansi.POSTFIX));
    }

    @Test
    public void ColoredPrint_AnsiCode_GenerateForAllProperties() {
        // ARRANGE
        UnixColoredPrinter printer = new UnixColoredPrinter();
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
        UnixColoredPrinter printer = new UnixColoredPrinter();
        Ansi.Attribute attr = Ansi.Attribute.LIGHT;
        Ansi.FColor fColor = Ansi.FColor.NONE;
        Ansi.BColor bColor = Ansi.BColor.NONE;

        // ACT
        String ansiCode = printer.generateCode(attr, fColor, bColor);
        String[] codeTokens = ansiCode.split(Pattern.quote(Ansi.SEPARATOR));

        // ASSERT
        assertThat("Code contains all attributes", codeTokens.length, equalTo(3));
        assertThat("Code contains attribute", codeTokens[0], containsString(attr.toString()));
        assertThat(codeTokens[1], equalTo(""));
        assertThat(codeTokens[2], equalTo("" + Ansi.POSTFIX));
    }

    @Test
    public void ColoredPrint_AnsiCode_GenerateForForegroundColorOnly() {
        // ARRANGE
        UnixColoredPrinter printer = new UnixColoredPrinter();
        Ansi.Attribute attr = Ansi.Attribute.NONE;
        Ansi.FColor fColor = Ansi.FColor.RED;
        Ansi.BColor bColor = Ansi.BColor.NONE;

        // ACT
        String ansiCode = printer.generateCode(attr, fColor, bColor);
        String[] codeTokens = ansiCode.split(Pattern.quote(Ansi.SEPARATOR));

        // ASSERT
        assertThat("Code contains all attributes", codeTokens.length, equalTo(3));
        assertThat(codeTokens[0], equalTo(Ansi.PREFIX + ""));
        assertThat("Code contain foreground color", codeTokens[1], containsString(fColor.toString()));
        assertThat(codeTokens[2], equalTo("" + Ansi.POSTFIX));
    }

    @Test
    public void ColoredPrint_AnsiCode_GenerateForBackgroundColorOnly() {
        // ARRANGE
        UnixColoredPrinter printer = new UnixColoredPrinter();
        Ansi.Attribute attr = Ansi.Attribute.NONE;
        Ansi.FColor fColor = Ansi.FColor.NONE;
        Ansi.BColor bColor = Ansi.BColor.MAGENTA;

        // ACT
        String ansiCode = printer.generateCode(attr, fColor, bColor);
        String[] codeTokens = ansiCode.split(Pattern.quote(Ansi.SEPARATOR));

        // ASSERT
        assertThat("Code contains all attributes", codeTokens.length, equalTo(3));
        assertThat(codeTokens[0], equalTo(Ansi.PREFIX + ""));
        assertThat(codeTokens[1], equalTo(""));
        assertThat("Code contain background color", codeTokens[2], containsString(bColor.toString()));
    }

    /*

    CLEAR("0"), BOLD("1"), LIGHT("1"), DARK("2"), UNDERLINE("4"), REVERSE("7"), HIDDEN("8"), NONE("");
    BLACK("40"), RED("41"), GREEN("42"), YELLOW("43"), BLUE("44"), MAGENTA("45"), CYAN("46"), WHITE("47"), NONE("");
    BLACK("30"), RED("31"), GREEN("32"), YELLOW("33"), BLUE("34"), MAGENTA("35"), CYAN("36"), WHITE("37"), NONE("");

     */
}