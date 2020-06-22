package com.diogonunes.jcolor.tests.acceptance;

import org.junit.Ignore;

public class TestHumanEye {

    @Ignore
    public void ShouldLookGood() {

//        // =============================
//        // Example of a terminal Printer
//        // =============================
//
//        Printer p = new Printer.Builder(Types.TERM).build();
//        p.println(p);
//        p.println("This is a normal message.");
//        p.errorPrintln("This is an error message.");
//        p.debugPrintln("This debug message is always printed.");
//        p = new Printer.Builder(Types.TERM).level(1).timestamping(false).build();
//        p.println(p);
//        p.debugPrintln("This is printed because its level is <= 1", 1);
//        p.debugPrintln("This isn't printed because its level is > 1", 2);
//        p.setLevel(2);
//        p.debugPrintln("Now this is printed because its level is <= 2", 2);
//
//        // =======================================================
//        // Example of a Colored terminal Printer (WINDOWS or UNIX)
//        // =======================================================
//        System.out.println();
//
//        //setting a format for all messages
//        ColoredPrinter cp = new ColoredPrinter.Builder(0, false)
//                .foreground(FColor.WHITE).background(BColor.BLUE)   //setting format
//                .build();
//        cp.println(cp);
//        cp.println("This printer will always format text with WHITE font on BLUE background.");
//        cp.setAttribute(Attribute.REVERSE);
//        cp.println("From now on, that format is reversed.");
//        System.out.println("ColoredPrinters do not affect System.* format.");
//        cp.print("Even if");
//        System.out.print(" you mix ");
//        cp.println("the two.");
//
//        //using multiple printers for diff purposes
//        ColoredPrinter cpWarn = new ColoredPrinter.Builder(1, true)
//                .foreground(FColor.YELLOW)
//                .build();
//        ColoredPrinter cpInfo = new ColoredPrinter.Builder(1, true)
//                .foreground(FColor.CYAN)
//                .build();
//        cpWarn.println("This printer displays timestamps and warning messages.");
//        cpInfo.println("And this printer can be used for info messages.");
//
//        //overriding format per message
//        cp = new ColoredPrinter.Builder(1, false)
//                .build();
//        cp.print("This example used JCDP 4.0.0   ");
//        cp.print("\tMADE ", Attribute.BOLD, FColor.YELLOW, BColor.GREEN);
//        cp.println("IN PORTUGAL", Attribute.BOLD, FColor.YELLOW, BColor.RED);
//        cp.println("I hope you find it useful ;)");
//
//        assertThat("This test is for humans only, so it always passes on CI", true, is(true));
    }
}
