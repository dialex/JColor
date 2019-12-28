package com.diogonunes.jcdp.tests.unit;

import com.diogonunes.jcdp.bw.Printer;
import com.diogonunes.jcdp.bw.impl.TerminalPrinter;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static com.diogonunes.jcdp.Constants.DATE_FORMAT_ISO8601;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestPrinterBuilder {

    @Test
    public void Printer_Creation_BuilderExists() {
        // ARRANGE

        // ACT
        Printer.Builder printerBuilder = new Printer.Builder(Printer.Types.TERM);

        // ASSERT
        assertThat(printerBuilder, not(equalTo(null)));
        assertThat(printerBuilder, instanceOf(Printer.Builder.class));
    }

    @Test
    public void Printer_Creation_BuilderReturnsTerminalPrinter() {
        // ARRANGE
        Printer.Builder b = new Printer.Builder(Printer.Types.TERM);

        // ACT
        Printer printer = b.build();

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer, instanceOf(Printer.class));
        assertThat("Implementation is TerminalPrinter", printer.toString(), containsString(TerminalPrinter.class.getSimpleName()));
    }

    @Test
    public void Printer_Creation_BuilderHandlesLevel() {
        // ARRANGE
        Printer.Builder b = new Printer.Builder(Printer.Types.TERM);
        int number = 2;

        // ACT
        Printer printer = b.level(number).build();

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer.getLevel(), equalTo(number));
    }

    @Test
    public void Printer_Creation_BuilderChaining() {
        // ARRANGE
        Printer.Builder b = new Printer.Builder(Printer.Types.TERM);
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_ISO8601);
        int number = 3;

        // ACT
        Printer printer = b.timestamping(true).level(number).withFormat(df).build();

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer.getLevel(), equalTo(number));
    }

    @Test
    public void Printer_Creation_PassingBuilderToConstructor() {
        // ARRANGE
        Printer.Builder b = new Printer.Builder(Printer.Types.TERM);
        int number = 3;

        // ACT
        b.level(3).timestamping(true);
        Printer printer = new Printer(b);

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer.getLevel(), equalTo(number));
    }
}
