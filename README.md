#JCDP

**Java Colored Debug Printer** (JCDP) is a Java library that offers you a convenient way to print colored messages or debug messages on a terminal.

###Screenshot

![This is a screenshot of the example code below, running at Ubuntu.](http://www.diogonunes.com/work/jcdp/img/ScreenshotNIX.png)

###Download

You can download the .jar packages at [JCDP's official webpage](http://diogonunes.com/work/jcdp/#download). The Unix-only version is lighter and has no third-party dependencies. The Windows version requires including an additional library called [JAnsi](https://github.com/fusesource/jansi).

###Example

The following code should produce [this result](http://www.diogonunes.com/work/jcdp/#example).

```java
package print.test;

import print.Printer;
import print.Printer.Types;
import print.color.ColoredPrinter;
import print.color.Ansi.*;
import print.exception.InvalidArgumentsException;

public class ExampleApp {
    public static void main(String[] args) throws InvalidArgumentsException {

        //example of a terminal Printer
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

        //example of a Colored terminal Printer (WINDOWS or UNIX)
        ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                                .foreground(FColor.WHITE).background(BColor.BLUE)   //setting format
                                .build();
            //printing according to that format
        cp.println(cp);
        cp.setAttribute(Attribute.REVERSE);
        cp.println("This is a normal message (with format reversed).");
            //reseting the terminal to its default colors
        cp.clear();
        cp.print(cp.getDateTime(), Attribute.NONE, FColor.CYAN, BColor.BLACK);
        cp.debugPrintln(" This debug message is always printed.");
        cp.clear();
        cp.print("This example used JCDP 1.25   ");
            //temporarily overriding that format
        cp.print("\tMADE ", Attribute.BOLD, FColor.YELLOW, BColor.GREEN);
        cp.print("IN PORTUGAL\t\n", Attribute.BOLD, FColor.YELLOW, BColor.RED);
        cp.println("I hope you find it useful ;)");

        cp.clear(); //don't forget to clear the terminal's format before exiting
    }
}
```

###Documentation

For more information about how to use this library please check [JCDP's official webpage](http://diogonunes.com/work/jcdp/). If you want to read the *javadoc* you can check the `doc` folder or read it [online](http://diogonunes.com/work/jcdp/doc/index.html).

###FAQ

Q: **I'm running on Windows and there's no colored output, only some weird codes.**
A: Make sure you included `JAnsi.jar` and that you created a `ColoredPrinterWIN` object. If you want to solve this problem during runtime, you might create a method that checks which OS you're running on, like so:

```java
private ColoredPrinter getPrinter(FColor frontColor, BColor backColor) {

    String os = System.getProperty("os.name");
    //System.out.println("DETECTED OS: " + os);

    if (os.toLowerCase().startsWith("win")) {
        return new ColoredPrinterWIN.Builder(1, false)
            .foreground(frontColor).background(backColor).build();
    } else {
        return new ColoredPrinter.Builder(1, false)
            .foreground(frontColor).background(backColor).build();
    }
    
}
```

###License

JPrinter  Copyright (C) 2011  Diogo Nunes
This program is licensed under the terms of the MIT License and it comes with ABSOLUTELY NO WARRANTY.
For more details check LICENSE.
