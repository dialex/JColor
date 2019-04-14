package com.diogonunes.jcdp.tests.unit;

import com.diogonunes.jcdp.bw.Printer;
import com.diogonunes.jcdp.bw.api.IPrinter;
import com.diogonunes.jcdp.bw.impl.FilePrinter;
import org.junit.*;

import java.io.File;
import java.io.FileNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for TerminalPrinter class.
 *
 * @author Diogo Nunes
 * @version 3.0
 */
public class TestFilePrinter {

    static private File TEST_DIR = new File("./test_output/");
    static private File LOG_FILE = new File(TEST_DIR, "custom-name.log");
    static private File DEFAULT_LOG_FILE = new File( "./JCDP.log");

    @BeforeClass
    public static void init() {
        if(!TEST_DIR.isDirectory())
            TEST_DIR.mkdirs();
    }

    @AfterClass
    public static void reset() {

    }

    @Before
    public void setUp() {
        if (LOG_FILE.exists())
            LOG_FILE.delete();
        if (DEFAULT_LOG_FILE.exists())
            DEFAULT_LOG_FILE.delete();
    }

    @Test
    public void Printer_Builder_ReturnsFilePrinterBasedOnType() {
        // ARRANGE
        Printer.Builder b = new Printer.Builder(Printer.Types.FILE);

        // ACT
        IPrinter printer = b.build();

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer, instanceOf(Printer.class));
        assertThat("Implementation is FilePrinter", printer.toString(), containsString(FilePrinter.class.getSimpleName()));
        assertThat(DEFAULT_LOG_FILE.exists(), is(true));
    }

//    @Test
//    public void FilePrinter_Constructor_UsesDefaults() {
//        // ARRANGE
//
//        // ACT
//        FilePrinter printer = new FilePrinter();
//        //TODO
//
//        // ASSERT
//        assertThat(printer, not(equalTo(null)));
//        assertThat(printer.isLoggingTimestamps(), equalTo(false));
//        assertThat(printer.isLoggingDebug(), equalTo(true));
//        assertThat(printer.getLevel(), equalTo(0));
//        assertThat(DEFAULT_LOG_FILE.exists(), is(true));
//    }

    @Test
    public void FilePrinter_Constructor_UsesBuilderConfig() {
        // ARRANGE
        boolean flag = true;
        int number = 1;
        FilePrinter.Builder builder = new FilePrinter.Builder(LOG_FILE, number, flag);
        FilePrinter printer = null;

        try {
            // ACT
            printer = new FilePrinter(builder);
        } catch (FileNotFoundException | SecurityException e) {
            // Nothing to do, this flow is checked on another test
        } finally {
            // ASSERT
            assertThat(printer, not(equalTo(null)));
            assertThat(printer.getLevel(), equalTo(number));
            assertThat(printer.isLoggingTimestamps(), equalTo(flag));
            assertThat(LOG_FILE.exists(), is(true));
        }
    }

}