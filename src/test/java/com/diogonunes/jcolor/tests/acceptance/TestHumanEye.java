package com.diogonunes.jcolor.tests.acceptance;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import org.junit.jupiter.api.Disabled;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;
import static com.diogonunes.jcolor.tests.unit.DataGenerator.randomInt;

public class TestHumanEye {

    @Disabled
    public void ShouldLookGood() {

        /*
        ==============
        Usage examples
        ==============
        */

        // Use Case 1: use Ansi.colorize() to format inline
        System.out.println(colorize("This text will be yellow on magenta", YELLOW_TEXT(), MAGENTA_BACK()));
        System.out.println("\n");

        // Use Case 2: compose Attributes to create your desired format
        Attribute[] myFormat = new Attribute[]{RED_TEXT(), YELLOW_BACK(), BOLD()};
        System.out.println(colorize("This text will be red on yellow", myFormat));
        System.out.println("\n");

        // Use Case 3: AnsiFormat is syntactic sugar for an array of Attributes
        AnsiFormat fWarning = new AnsiFormat(GREEN_TEXT(), BLUE_BACK(), BOLD());
        System.out.println(colorize("AnsiFormat is just a pretty way to declare formats", fWarning));
        System.out.println(fWarning.format("...and use those formats without calling colorize() directly"));
        System.out.println("\n");

        // Use Case 4: you can define your formats and use them throughout your code
        AnsiFormat fInfo = new AnsiFormat(CYAN_TEXT());
        AnsiFormat fError = new AnsiFormat(YELLOW_TEXT(), RED_BACK());
        System.out.println(fInfo.format("This info message will be cyan"));
        System.out.println("This normal message will not be formatted");
        System.out.println(fError.format("This error message will be yellow on red"));
        System.out.println("\n");

        // Use Case 5: we support bright colors
        AnsiFormat fNormal = new AnsiFormat(MAGENTA_BACK(), YELLOW_TEXT());
        AnsiFormat fBright = new AnsiFormat(BRIGHT_MAGENTA_BACK(), BRIGHT_YELLOW_TEXT());
        System.out.println(fNormal.format("You can use normal colors ") + fBright.format(" and bright colors too"));

        // Use Case 6: we support 8-bit colors
        System.out.println("Any 8-bit color (0-255), as long as your terminal supports it:");
        for (int i = 0; i <= 255; i++) {
            Attribute textColor = TextColor(i);
            System.out.print(colorize(String.format("%4d", i), textColor));
        }
        System.out.println("\n");

        // Use Case 7: we support true colors (RGB)
        System.out.println("Any TrueColor (RGB), as long as your terminal supports it:");
        for (int i = 0; i <= 300; i++) {
            Attribute bkgColor = BackColor(randomInt(255), randomInt(255), randomInt(255));
            System.out.print(colorize("   ", bkgColor));
        }
        System.out.println("\n");

        // Credits
        System.out.print("This example used JColor 5.0.0   ");
        System.out.print(colorize("\tMADE ", BOLD(), BRIGHT_YELLOW_TEXT(), GREEN_BACK()));
        System.out.println(colorize("IN PORTUGAL\t", BOLD(), BRIGHT_YELLOW_TEXT(), RED_BACK()));
        System.out.println("I hope you find it useful ;)");
    }
}
