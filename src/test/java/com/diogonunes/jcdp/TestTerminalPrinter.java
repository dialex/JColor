package com.diogonunes.jcdp;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.diogonunes.jcdp.impl.TerminalPrinter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for TerminalPrinter class.
 *
 * @version 2.0
 * @author Diogo Nunes
 */
public class TestTerminalPrinter {

    /* redirects stdout */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    /* redirects stderr */
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    /* instance of printer to test */
    private TerminalPrinter printer = null;

    /**
     * Redirects standard outputs to PrintStream objects so the output can be
     * tested/compared later.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        outContent.reset();
        errContent.reset();
        System.setOut(null);
        System.setErr(null);
    }

    /**
     * Test method for {@link print.TerminalPrinter#print(java.lang.String)}.
     * Test method for
     * {@link print.TerminalPrinter#errorPrint(java.lang.String)}.
     *
     * @throws IllegalArgumentException
     */
    @Test
    public void testPrint() throws IllegalArgumentException {
        // ARRANGE
        printer = new TerminalPrinter.Builder(0, false).build();
        String msg = "Normal/Error ";

        // ACT
        printer.print(msg);
        printer.errorPrint(msg);

        // ASSERT
        assertThat(outContent.toString().equals(msg), is(true));
        assertThat(errContent.toString().equals(msg), is(true));
    }

    /**
     * Test method for
     * {@link print.TerminalPrinter#debugPrint(java.lang.String, int)}.
     *
     * @throws IllegalArgumentException
     */
    @Test
    public void testDebugPrintWithLevels() throws IllegalArgumentException {
        // ARRANGE
        printer = new TerminalPrinter.Builder(2, false).build();
        String msg = "Debug ";

        // ACT
        printer.debugPrint(msg, 2);
        printer.debugPrint(msg, 3); /* not printed */

        // ASSERT
        assertThat(outContent.toString().equals(msg), is(true));
        assertThat(outContent.toString().equals(msg + msg), is(false));
    }

    /**
     * Test method for
     * {@link print.TerminalPrinter#debugPrint(java.lang.String, int)}. Test
     * method for {@link print.TerminalPrinter#setLevel(int)}.
     *
     * @throws IllegalArgumentException
     */
    @Test
    public void testDebugPrintChangingLevel() throws IllegalArgumentException {
        // ARRANGE
        printer = new TerminalPrinter.Builder(1, false).build();
        String msg = "Debug ";

        // ACT
        printer.debugPrint(msg, 2); /* not printed */
        printer.setLevel(3);
        printer.debugPrint(msg, 2);
        printer.setLevel(1);
        printer.debugPrint(msg, 3); /* not printed */

        // ASSERT
        assertThat(outContent.toString().equals(msg), is(true));
        assertThat(outContent.toString().equals(msg + msg), is(false));
        assertThat(outContent.toString().equals(msg + msg + msg), is(false));
    }

    /**
     * Test method for {@link print.TerminalPrinter#setDebugging(boolean)}. Test
     * method for {@link print.TerminalPrinter#canPrint(int)}.
     *
     * @throws IllegalArgumentException
     */
    @Test
    public void testDebugPrintOnOff() throws IllegalArgumentException {
        // ARRANGE
        printer = new TerminalPrinter.Builder(0, false).build();
        String msg = "Debug ";

        // ACT
        printer.debugPrint(msg, 0);
        printer.debugPrint(msg, 2);
        printer.setDebugging(false);
        printer.debugPrint(msg, 0); /* not printed */
        printer.debugPrint(msg, 2); /* not printed */
        printer.setLevel(1);
        printer.debugPrint(msg, 1);
        printer.debugPrint(msg, 2); /* not printed */

        // ASSERT
        assertThat(outContent.toString().equals(msg + msg + msg), is(true));
    }

    /**
     * Test method for {@link print.TerminalPrinter#setTimestamping(boolean)}.
     * Test method for {@link print.TerminalPrinter#print(java.lang.String)}.
     *
     * @throws IllegalArgumentException
     */
    @Test
    public void testTimestamping() throws IllegalArgumentException {
        // ARRANGE
        printer = new TerminalPrinter.Builder(1, false).build();
        String msg = "Message";
        String timestampedMsg = printer.getDateTime() + " " + msg;

        // ACT
        printer.errorPrint(msg); /* without timestamp */
        printer.setTimestamping(true);
        printer.print(msg); /* with timestamp */

        // ASSERT
        assertThat(errContent.toString().equals(msg), is(true));
        assertThat(outContent.toString().equals(timestampedMsg), is(true));
    }
}