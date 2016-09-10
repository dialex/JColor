package com.diogonunes.jcdp;

import com.diogonunes.jcdp.bw.impl.TerminalPrinter;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for TerminalPrinter class.
 *
 * @author Diogo Nunes
 * @version 2.0
 */
public class TestTerminalPrinter {

    private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private TerminalPrinter printer; // Printer under test

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

    @Before
    public void setUp() {
        printer = null;
    }

    @After
    public void tearDown() {
        outContent.reset();
        errContent.reset();
    }

    @Test
    public void Print_Message_DisplaysOnSysOut() {
        // ARRANGE
        printer = new TerminalPrinter.Builder(0, false).build();
        String msg = "Normal";

        // ACT
        printer.print(msg);

        // ASSERT
        assertThat(outContent.toString(), equalTo(msg));
    }

    @Test
    public void Print_ErrorMessage_DisplaysOnSysErr() {
        // ARRANGE
        printer = new TerminalPrinter.Builder(0, false).build();
        String msg = "Error";

        // ACT
        printer.errorPrint(msg);

        // ASSERT
        assertThat(errContent.toString(), equalTo(msg));
    }

    @Test
    public void Print_DebugMessage_DisplaysOnSysOut() {
        // ARRANGE
        printer = new TerminalPrinter.Builder(0, false).build();
        String msg = "Debug";

        // ACT
        printer.debugPrint(msg);

        // ASSERT
        assertThat(outContent.toString(), equalTo(msg));
    }

    @Test
    public void Print_DebugMessage_DisplayIfEnoughLevel() {
        // ARRANGE
        printer = new TerminalPrinter.Builder(2, false).build();
        String msgNoLevel = "Debug0";
        String msgLevelOne = "Debug1";
        String msgLevelTwo = "Debug2";

        // ACT
        printer.debugPrint(msgNoLevel, 0);
        printer.debugPrint(msgLevelOne, 1);
        printer.debugPrint(msgLevelTwo, 2);

        // ASSERT
        assertThat(outContent.toString(), containsString(msgNoLevel));
        assertThat(outContent.toString(), containsString(msgLevelOne));
        assertThat(outContent.toString(), containsString(msgLevelTwo));
    }

    @Test
    public void Print_DebugMessage_IgnoreIfLevelAbove() {
        // ARRANGE
        printer = new TerminalPrinter.Builder(2, false).build();
        String msgLevelTwo = "Debug2";
        String msgLevelThree = "Debug3";

        // ACT
        printer.debugPrint(msgLevelTwo, 2);
        printer.debugPrint(msgLevelThree, 3);

        // ASSERT
        assertThat(outContent.toString(), containsString(msgLevelTwo));
        assertThat("Ignores messages above current level", outContent.toString(), not(containsString(msgLevelThree)));
    }

    @Test
    public void Print_DebugMessage_DisplayAfterChangingLevel() {
        // ARRANGE
        printer = new TerminalPrinter.Builder(2, false).build();
        String msg = "Debug3";

        // ACT
        printer.debugPrint(msg, 3);
        printer.setLevel(3);
        printer.debugPrint(msg, 3);

        // ASSERT
        assertThat("After changing level message is printed", outContent.toString(), equalTo(msg));
    }

    @Test
    public void Print_DebugMessage_DisplayAfterEnablingDebug() {
        // ARRANGE
        printer = new TerminalPrinter.Builder(2, false).build();
        String msg = "Debug";

        // ACT
        printer.setDebugging(false);
        printer.debugPrint(msg, 2);
        printer.setDebugging(true);
        printer.debugPrint(msg, 2);

        // ASSERT
        assertThat("Disabling debug mutes that message type", outContent.toString(), equalTo(msg));
    }

    @Test
    public void Print_Message_DisplayTimestamp() {
        // ARRANGE
        printer = new TerminalPrinter.Builder(2, true).build();
        String msg = "Normal";
        String timestamp = printer.getDateTime();//TODO ignore seconds

        // ACT
        printer.print(msg);

        // ASSERT
        assertThat("Message is printed", outContent.toString(), containsString(msg));
        assertThat("Message includes timestamp", outContent.toString(), containsString(timestamp));
    }

    @Test
    public void Print_Message_DisplayTimestampAfterEnablingIt() {
        // ARRANGE
        printer = new TerminalPrinter.Builder(2, false).build();
        String msg = "Message";
        String timestamp = printer.getDateTime();
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
        printer = new TerminalPrinter.Builder(2, true).withFormat(timestampFormat).build();
        String msg = "Message";
        String timestamp = timestampFormat.format(new Date());

        // ACT
        printer.print(msg);

        // ASSERT
        assertThat(outContent.toString(), containsString(msg));
        assertThat("Timestamp uses custom format", outContent.toString(), containsString(timestamp));
    }
}