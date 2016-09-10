package com.diogonunes.jcdp.unit;

import com.diogonunes.jcdp.bw.Printer;
import com.diogonunes.jcdp.bw.api.IPrinter;
import com.diogonunes.jcdp.bw.impl.TerminalPrinter;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PrinterBuilderTests {

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
        IPrinter printer = b.build();

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
        IPrinter printer = b.level(number).build();

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer.getLevel(), equalTo(number));
    }

    @Test
    public void Printer_Creation_BuilderChaining() {
        // ARRANGE
        Printer.Builder b = new Printer.Builder(Printer.Types.TERM);
        boolean flag = true;
        int number = 3;

        // ACT
        IPrinter printer = b.timestamping(flag).level(number).build();
        System.out.print(printer.toString());

        // ASSERT
        assertThat(printer, not(equalTo(null)));
        assertThat(printer.getLevel(), equalTo(number));
    }
}
