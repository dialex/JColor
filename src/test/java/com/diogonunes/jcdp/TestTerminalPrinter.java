package com.diogonunes.jcdp;

import com.diogonunes.jcdp.bw.impl.TerminalPrinter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests for TerminalPrinter class.
 *
 * @author Diogo Nunes
 * @version 2.0
 */
public class TestTerminalPrinter {

    private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private TerminalPrinter printer = null; // instance of printer to test

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
    public void tearDown() throws Exception {
        outContent.reset();
        errContent.reset();
    }

    @Test
    public void Print_NormalMessage_DisplaysOnSysOut() {
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

//    /**
//     * Test method for
//     * {@link TerminalPrinter#debugPrint(Object, int)}.
//     */
//    @Test
//    public void testDebugPrintWithLevels() {
//        // ARRANGE
//        printer = new TerminalPrinter.Builder(2, false).build();
//        String msg = "Debug ";
//
//        // ACT
//        printer.debugPrint(msg, 2);
//        printer.debugPrint(msg, 3); /* not printed */
//
//        // ASSERT
//        assertThat(outContent.toString().equals(msg), is(true));
//        assertThat(outContent.toString().equals(msg + msg), is(false));
//    }
//
//    /**
//     * Test method for
//     * {@link TerminalPrinter#debugPrint(Object, int)}. Test
//     * method for {@link TerminalPrinter#setLevel(int)}.
//     */
//    @Test
//    public void testDebugPrintChangingLevel() {
//        // ARRANGE
//        printer = new TerminalPrinter.Builder(1, false).build();
//        String msg = "Debug ";
//
//        // ACT
//        printer.debugPrint(msg, 2); /* not printed */
//        printer.setLevel(3);
//        printer.debugPrint(msg, 2);
//        printer.setLevel(1);
//        printer.debugPrint(msg, 3); /* not printed */
//
//        // ASSERT
//        assertThat(outContent.toString().equals(msg), is(true));
//        assertThat(outContent.toString().equals(msg + msg), is(false));
//        assertThat(outContent.toString().equals(msg + msg + msg), is(false));
//    }
//
//    /**
//     * Test method for {@link TerminalPrinter#setDebugging(boolean)}. Test
//     * method for {@link TerminalPrinter#canPrint(int)}.
//     */
//    @Test
//    public void testDebugPrintOnOff() {
//        // ARRANGE
//        printer = new TerminalPrinter.Builder(0, false).build();
//        String msg = "Debug ";
//
//        // ACT
//        printer.debugPrint(msg, 0);
//        printer.debugPrint(msg, 2);
//        printer.setDebugging(false);
//        printer.debugPrint(msg, 0); /* not printed */
//        printer.debugPrint(msg, 2); /* not printed */
//        printer.setLevel(1);
//        printer.debugPrint(msg, 1);
//        printer.debugPrint(msg, 2); /* not printed */
//
//        // ASSERT
//        assertThat(outContent.toString().equals(msg + msg + msg), is(true));
//    }
//
//    /**
//     * Test method for {@link TerminalPrinter#setTimestamping(boolean)}.
//     * Test method for {@link TerminalPrinter#print(Object)}.
//     */
//    @Test
//    public void testTimestamping() {
//        // ARRANGE
//        printer = new TerminalPrinter.Builder(1, false).build();
//        String msg = "Message";
//        String timestampedMsg = printer.getDateTime() + " " + msg;
//
//        // ACT
//        printer.errorPrint(msg); /* without timestamp */
//        printer.setTimestamping(true);
//        printer.print(msg); /* with timestamp */
//
//        // ASSERT
//        assertThat(errContent.toString().equals(msg), is(true));
//        assertThat(outContent.toString().equals(timestampedMsg), is(true));
//    }
}