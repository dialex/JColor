package com.diogonunes.jcdp.tests.acceptance;

import com.diogonunes.jcdp.bw.Printer;
import com.diogonunes.jcdp.bw.Printer.Types;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;
import org.junit.Ignore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestHumanEye {

    @Ignore
    public void TestHowItLooksOnConsole() {

        // =============================
        // Example of a terminal Printer
        // =============================

        Printer p = new Printer.Builder(Types.TERM).build();
        p.println(p);
        p.println("This is a normal message.");
        p.errorPrintln("This is an error message.");
        p.debugPrintln("This debug message is always printed.");
        p = new Printer.Builder(Types.TERM).level(1).timestamping(false).build();
        p.println(p);
        p.debugPrintln("This is printed because its level is <= 1", 1);
        p.debugPrintln("This isn't printed because its level is > 1", 2);
        p.setLevel(2);
        p.debugPrintln("Now this is printed because its level is <= 2", 2);

        // =======================================================
        // Example of a Colored terminal Printer (WINDOWS or UNIX)
        // =======================================================

        ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                .foreground(FColor.WHITE).background(BColor.BLUE)   //setting format
                .build();

        //printing according to that format
        cp.println(cp);
        cp.setAttribute(Attribute.REVERSE);
        cp.println("This is a normal message (with format reversed).");

        //resetting the terminal to its default colors
        cp.clear();
        cp.print(cp.getDateFormatted(), Attribute.NONE, FColor.CYAN, BColor.NONE);
        cp.debugPrintln(" This debug message is always printed.");
        cp.clear();
        cp.print("This example used JCDP 2.0.3   ");

        //temporarily overriding that format
        cp.print("\tMADE ", Attribute.BOLD, FColor.YELLOW, BColor.GREEN);
        cp.println("IN PORTUGAL", Attribute.BOLD, FColor.YELLOW, BColor.RED);
        cp.println("I hope you find it useful ;)");

        cp.clear(); //don't forget to clear the terminal's format before exiting

        assertThat("This test is for humans only, so it always passes on CI", true, is(true));
    }
}
