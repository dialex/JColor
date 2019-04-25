package com.diogonunes.jcdp.tests.unit;

import com.diogonunes.jcdp.bw.Printer;
import com.diogonunes.jcdp.bw.api.IPrinter;
import com.diogonunes.jcdp.bw.impl.FilePrinter;
import helpers.DataGenerator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

/**
 * Tests for TerminalPrinter class.
 *
 * @author Diogo Nunes
 * @version 3.0
 */
public class TestFilePrinter {

    static private File TEST_DIR = new File("test_output/");
    static private File LOG_FILE = new File(TEST_DIR, "custom-name.log");
    static private File DEFAULT_LOG_FILE = new File("JCDP.log");

    @BeforeClass
    public static void init() {
        if (!TEST_DIR.isDirectory())
            TEST_DIR.mkdirs();
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

    @Test
    public void FilePrinter_Constructor_UsesDefaults() {
        // ARRANGE
        FilePrinter printer = null;

        try {
            // ACT
            printer = new FilePrinter();

            // ASSERT
            assertThat(printer, not(equalTo(null)));
            assertThat(printer.isLoggingTimestamps(), equalTo(false));
            assertThat(printer.isLoggingDebug(), equalTo(true));
            assertThat(printer.getLevel(), equalTo(0));
            assertThat(DEFAULT_LOG_FILE.exists(), is(true));
        } catch (FileNotFoundException | SecurityException e) {
            fail(); // this flow is checked on another test
        }
    }

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

            // ASSERT
            assertThat(printer, not(equalTo(null)));
            assertThat(printer.getLevel(), equalTo(number));
            assertThat(printer.isLoggingTimestamps(), equalTo(flag));
            assertThat(LOG_FILE.exists(), is(true));
        } catch (FileNotFoundException | SecurityException e) {
            fail(); // this flow is checked on another test
        }
    }

    @Test
    public void FilePrinter_Constructor_ThrowsExceptionOnFailedFileCreation() {
        // ARRANGE
        String invalidFilename = "";
        FilePrinter.Builder builder = new FilePrinter.Builder(new File(invalidFilename), 0, false);
        Exception errorOccurred = null;

        try {
            // ACT
            new FilePrinter(builder);
        } catch (Exception e) {
            errorOccurred = e;
        } finally {
            // ASSERT
            //FIXME use a simple exception assertion using jUnit 5
            assertThat(errorOccurred, not(equalTo(null)));
            assertThat(errorOccurred, is(instanceOf(FileNotFoundException.class)));
        }
    }

    @Test
    public void ToString_Description_DisplayName() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter();

            // ACT
            String description = printer.toString();

            // ASSERT
            assertThat(description, containsString(FilePrinter.class.getSimpleName()));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void ToString_Description_DisplayProperties() {
        try {
            // ARRANGE
            boolean flag = true;
            int number = 1;
            String separator = " | ";
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, number, flag).build();

            // ACT
            String description = printer.toString();
            String[] descTokens = description.split(Pattern.quote(separator));

            // ASSERT
            assertThat(String.format("Properties are separated by '%1$s'", separator), descTokens.length, greaterThan(0));
            assertThat("Level property is displayed", descTokens[1], containsString("level"));
            assertThat("Level property value is displayed", descTokens[1], containsString(String.valueOf(number)));
            assertThat("Timestamp property is displayed", descTokens[2], containsString("timestamping"));
            assertThat("Timestamp property value is displayed", descTokens[2], containsString(String.valueOf(flag)));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_Message_DisplayOnSysOut() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 0, false).build();
            String msg = DataGenerator.createMsg();

            // ACT
            printer.println(msg);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat(fileContents, containsString(msg));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_Message_DisplayTimestamp() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 2, true).build();
            String msg = DataGenerator.createMsg();
            String timestamp = DataGenerator.getCurrentDate(new SimpleDateFormat(DataGenerator.DATE_FORMAT_ISO8601));
            timestamp = timestamp.substring(0, timestamp.lastIndexOf(":")); // ignore seconds

            // ACT
            printer.print(msg);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat("Message is printed", fileContents, containsString(msg));
            assertThat("Message includes timestamp", fileContents, containsString(timestamp));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_Message_DisplayTimestampAfterEnablingIt() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 2, false).build();
            String msg = DataGenerator.createMsg();
            String timestamp = DataGenerator.getCurrentDate(new SimpleDateFormat(DataGenerator.DATE_FORMAT_ISO8601));
            timestamp = timestamp.substring(0, timestamp.lastIndexOf(":")); // ignore seconds

            // ACT
            printer.print(msg);
            printer.setTimestamping(true);
            printer.errorPrint(msg);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat(fileContents, containsString(msg));
            assertThat(fileContents, not(containsString(timestamp)));
            assertThat("After enabling timestamping, message includes it", fileContents, containsString(timestamp));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_Message_DisplayTimestampWithCustomDateFormat() {
        try {
            // ARRANGE
            DateFormat timestampFormat = new SimpleDateFormat("yy.MM.dd");
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 2, true).withFormat(timestampFormat).build();
            String msg = DataGenerator.createMsg();
            String timestamp = DataGenerator.getCurrentDate(timestampFormat);

            // ACT
            printer.print(msg);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat(fileContents, containsString(msg));
            assertThat("Timestamp uses custom format", fileContents, containsString(timestamp));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_ErrorMessage_DisplayOnSysErr() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 0, false).build();
            String msg = DataGenerator.createErrorMsg();

            // ACT
            printer.errorPrintln(msg);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat(fileContents, equalTo(msg + "\n"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_DebugMessage_DisplayOnSysOut() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 0, false).build();
            String msg = DataGenerator.createMsgWithId(0);

            // ACT
            printer.debugPrintln(msg);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat(fileContents, equalTo(msg + "\n"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_DebugMessage_DisplayAfterEnablingDebug() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 2, false).build();
            String msg = DataGenerator.createMsgWithId(2);

            // ACT
            printer.setDebugging(false);
            printer.debugPrint(msg, 2);
            printer.setDebugging(true);
            printer.debugPrint(msg, 2);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat("Disabling debug mutes that message type", fileContents, equalTo(msg));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_DebugMessage_DisplayWithoutLevel() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 1, false).build();
            String msgNoLevel = DataGenerator.createMsg();
            String msgLevelTwo = DataGenerator.createMsgWithId(2);

            // ACT
            printer.debugPrint(msgNoLevel);
            printer.debugPrint(msgLevelTwo, 2);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat(fileContents, containsString(msgNoLevel));
            assertThat(fileContents, not(containsString(msgLevelTwo)));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_DebugMessage_DisplayIfEnoughLevel() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 2, false).build();
            String msgLevelZero = DataGenerator.createMsgWithId(0);
            String msgLevelOne = DataGenerator.createMsgWithId(1);
            String msgLevelTwo = DataGenerator.createMsgWithId(2);

            // ACT
            printer.debugPrint(msgLevelZero, 0);
            printer.debugPrint(msgLevelOne, 1);
            printer.debugPrint(msgLevelTwo, 2);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat(fileContents, containsString(msgLevelZero));
            assertThat(fileContents, containsString(msgLevelOne));
            assertThat(fileContents, containsString(msgLevelTwo));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_DebugMessage_DisplayAfterChangingLevel() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 2, false).build();
            String msg = DataGenerator.createMsgWithId(3);

            // ACT
            printer.debugPrint(msg, 3);
            printer.setLevel(3);
            printer.debugPrint(msg, 3);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat("After changing level message is printed", fileContents, equalTo(msg));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_DebugMessage_IgnoreIfLevelAbove() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 2, false).build();
            String msgLevelTwo = DataGenerator.createMsgWithId(2);
            String msgLevelThree = DataGenerator.createMsgWithId(3);

            // ACT
            printer.debugPrint(msgLevelTwo, 2);
            printer.debugPrint(msgLevelThree, 3);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat(fileContents, containsString(msgLevelTwo));
            assertThat("Ignores messages above current level", fileContents, not(containsString(msgLevelThree)));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Print_DebugMessage_IgnoreDebugIfDisabled() {
        try {
            // ARRANGE
            FilePrinter printer = new FilePrinter.Builder(LOG_FILE, 2, false).build();
            String msg = DataGenerator.createMsg();

            // ACT
            printer.setDebugging(false);
            printer.debugPrint(msg);

            // ASSERT
            String fileContents = readFile(LOG_FILE.getCanonicalPath());
            assertThat(fileContents, not(equalTo("")));
            assertThat(fileContents, not(containsString(msg)));
        } catch (Exception e) {
            fail();
        }
    }

    private String readFile(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath)));
    }
}