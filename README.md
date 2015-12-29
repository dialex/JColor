#JCDP

<img src="https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/JCDP-logo.png" width="150">

**Java Colored Debug Printer** (JCDP) is a Java library that offers you a convenient way to print colored messages or debug messages on a terminal.

###Screenshots

![NIX screenshot](https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/ScreenshotNIX.png)
Running on Ubuntu

![WIN screenshot](https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/ScreenshotWIN.png)
Running on Windows

###Example

The screenshots above were produced by running this example code:

```java
package com.diogonunes.jcdp.main;

import com.diogonunes.jcdp.*;
import com.diogonunes.jcdp.Printer.*;
import com.diogonunes.jcdp.color.*;
import com.diogonunes.jcdp.color.api.Ansi.*;

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

###Downloads

If you don't want to compile the source you can just download the `jar` files below. They're are ready to be imported to your project. Don't forget to choose the one right for your Operating System.

- [Download NIX-only JAR](http://www.diogonunes.com/assets/downloadmanager/click.php?id=8): the Unix `jar` is lighter and has no 3rd-party dependencies.
- [Download WIN/NIX JAR](http://www.diogonunes.com/assets/downloadmanager/click.php?id=9): the Windows `jar` includes an additional library called [JAnsi](https://github.com/fusesource/jansi).

###Documentation

![UML diagram](https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/JCDP-UML.png)

[**Javadoc**](http://dialex.github.io/JCDP/javadoc/) is available, listing all methods, inputs and behaviors.

####FAQ

**Q**: I'm running on Windows and there's no colored output, only some weird codes.<br/>
**A**: Make sure you included `JAnsi.jar` and that you created a `ColoredPrinterWIN` object. If you want to solve this problem during runtime, you might create a method that checks which OS you're running on, like so:

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

JPrinter  Copyright (C) 2011  [Diogo Nunes](http://www.diogonunes.com/)
This program is licensed under the terms of the MIT License and it comes with ABSOLUTELY NO WARRANTY. For more details check LICENSE.

If this software was useful to you, consider ♥ [making a donation](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=88NSA22HBX2PA).
