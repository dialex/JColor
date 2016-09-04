package com.diogonunes.jcdp;

import com.diogonunes.jcdp.impl.TerminalPrinter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for TerminalPrinter class.
 *
 * @author Diogo Nunes
 * @version 2.0
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
     * @throws Exception Some unexpected error.
     */
    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * @throws Exception Some unexpected error.
     */
    @After
    public void tearDown() throws Exception {
        outContent.reset();
        errContent.reset();
        System.setOut(null);
        System.setErr(null);
    }

    /**
     * Test method for {@link com.diogonunes.jcdp.impl.TerminalPrinter#print(Object)}.
     * Test method for
     * {@link com.diogonunes.jcdp.impl.TerminalPrinter#errorPrint(Object)}.
     */
    @Test
    public void testPrint() {
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
     * {@link com.diogonunes.jcdp.impl.TerminalPrinter#debugPrint(Object, int)}.
     */
    @Test
    public void testDebugPrintWithLevels() {
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
     * {@link com.diogonunes.jcdp.impl.TerminalPrinter#debugPrint(Object, int)}. Test
     * method for {@link com.diogonunes.jcdp.impl.TerminalPrinter#setLevel(int)}.
     */
    @Test
    public void testDebugPrintChangingLevel() {
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
     * Test method for {@link com.diogonunes.jcdp.impl.TerminalPrinter#setDebugging(boolean)}. Test
     * method for {@link com.diogonunes.jcdp.impl.TerminalPrinter#canPrint(int)}.
     */
    @Test
    public void testDebugPrintOnOff() {
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
     * Test method for {@link com.diogonunes.jcdp.impl.TerminalPrinter#setTimestamping(boolean)}.
     * Test method for {@link com.diogonunes.jcdp.impl.TerminalPrinter#print(Object)}.
     */
    @Test
    public void testTimestamping() {
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