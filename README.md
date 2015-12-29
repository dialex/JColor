#JCDP

<img src="https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/JCDP-logo.png" width="150">

**Java Colored Debug Printer** (JCDP) is a Java library that offers you a convenient way to print colored messages or debug messages on a terminal.

###Screenshots

![MacTerminal screenshot](https://raw.githubusercontent.com/xafero/JCDP/master/doc/img/mac-terminal.png)
*Running on Mac OS X Yosemite (Terminal)*

![MacIterm screenshot](https://raw.githubusercontent.com/xafero/JCDP/master/doc/img/mac-iterm.png)
*Running on Mac OS X Yosemite (iTerm)*

![Win8cmd screenshot](https://raw.githubusercontent.com/xafero/JCDP/master/doc/img/win8-cmd.png)
*Running on Windows 8/8.1 (cmd)*

![Win7cmd screenshot](https://raw.githubusercontent.com/xafero/JCDP/master/doc/img/win7-cmd.png)
*Running on Windows 7 (cmd)*

![Win7bash screenshot](https://raw.githubusercontent.com/xafero/JCDP/master/doc/img/win7-bash.png)
*Running on Windows 7 (bash)*

![MateTerminal screenshot](https://raw.githubusercontent.com/xafero/JCDP/master/doc/img/mate-terminal.png)
*Running on Mate Linux 17.2 (Terminal)*

![MateXterm screenshot](https://raw.githubusercontent.com/xafero/JCDP/master/doc/img/mate-xterm.png)
*Running on Mate Linux 17.2 (xterm)*

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

###Build tools

Import the library into your own project with Maven (Nr. 1) oder Gradle (Nr. 2):
 
```xml
<dependency>
	<groupId>com.github.xafero</groupId>
	<artifactId>JCDP</artifactId>
	<version>v1.26</version>
</dependency>
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
    </repository>
</repositories>
```

```json
allprojects {
	repositories {
		maven { url "https://jitpack.io" }
	}
}
dependencies {
	compile 'com.github.xafero:JCDP:v1.26'
}
```

###Downloads

If you don't want to compile the source you can just download the `jar` files below. They're are ready to be imported to your project. You should choose the first complete release which targets Windows and Unix, too.

- [Download WIN/NIX JAR](https://github.com/xafero/JCDP/releases/download/v1.26/JCDP-1.26.jar): the Windows `jar` includes an additional library called [JAnsi](https://github.com/fusesource/jansi).
- [Download UNIX-only JAR](https://github.com/xafero/JCDP/releases/download/v1.26/JCDP-1.26-min.jar): the Unix `jar` is lighter and has no 3rd-party dependencies.

###License

JPrinter  Copyright (C) 2011  [Diogo Nunes](http://www.diogonunes.com/)
This program is licensed under the terms of the MIT License and it comes with ABSOLUTELY NO WARRANTY. For more details check LICENSE.

If this software was useful to you, consider ♥ [making a donation](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=88NSA22HBX2PA).
