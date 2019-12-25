# <img src="https://raw.githubusercontent.com/dialex/JCDP/master/.github/img/JCDP-logo.png" width="150">

[![Travis build](https://img.shields.io/travis/dialex/jcdp)](https://travis-ci.org/dialex/JCDP) [![Maven Central](https://img.shields.io/maven-central/v/com.diogonunes/JCDP)](https://mvnrepository.com/artifact/com.diogonunes/JCDP) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/faaed58a577d4c3099cf8d6d4d572fb8)](https://www.codacy.com/app/dialex-nunes/JCDP?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dialex/JCDP&amp;utm_campaign=Badge_Grade) [![License](https://img.shields.io/github/license/dialex/JCDP.svg)](https://github.com/dialex/JCDP/blob/master/LICENSE) [![Donate](https://img.shields.io/badge/donate-%E2%99%A5%20-E91E63.svg)](https://www.paypal.me/dialexnunes/)

**Java Colored Debug Printer** (JCDP) offers you an easy syntax to print messages with a colored font or background on a terminal. It also provides custom levels of debug logging.

### Screenshots

![Ubuntu screenshot](https://raw.githubusercontent.com/dialex/JCDP/master/.github/img/ubuntu-console.png)

*Running on Ubuntu*

![MacIterm screenshot](https://raw.githubusercontent.com/dialex/JCDP/master/.github/img/mac-iterm.png)

*Running on MacOS X Yosemite (iTerm)*

![Win8cmd screenshot](https://raw.githubusercontent.com/dialex/JCDP/master/.github/img/win8-cmd.png)

*Running on Windows 8.1 (cmd)*

### Usage

The screenshots above were produced by running this example code:

```java
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
System.out.println("");

//setting a format for all messages
ColoredPrinter cp = new ColoredPrinter.Builder(0, false)
        .foreground(FColor.WHITE).background(BColor.BLUE)   //setting format
        .build();
cp.println(cp);
cp.println("This printer will always format text with WHITE font on BLUE background.");
cp.setAttribute(Attribute.REVERSE);
cp.println("From now on, that format is reversed.");
System.out.println("ColoredPrinters do not affect System.* format.");
cp.print("Even if");
System.out.print(" you mix ");
cp.println("the two.");

//using multiple printers for diff purposes
ColoredPrinter cpWarn = new ColoredPrinter.Builder(1, true)
        .foreground(FColor.YELLOW)
        .build();
ColoredPrinter cpInfo = new ColoredPrinter.Builder(1, true)
        .foreground(FColor.CYAN)
        .build();
cpWarn.println("This printer displays timestamps and warning messages.");
cpInfo.println("And this printer can be used for info messages.");

//overriding format per message
cp = new ColoredPrinter.Builder(1, false)
        .build();
cp.print("This example used JCDP 3.0.4   ");
cp.print("\tMADE ", Attribute.BOLD, FColor.YELLOW, BColor.GREEN);
cp.println("IN PORTUGAL", Attribute.BOLD, FColor.YELLOW, BColor.RED);
cp.println("I hope you find it useful ;)");
```

### Installation

You can import this dependency through Maven or Gradle, just [pick a version](https://mvnrepository.com/artifact/com.diogonunes/JCDP).

#### Requirements

- JCDP `v3.*` (latest) supports Java +8, Linux, Mac, Windows
- JCDP `v2.*` (legacy) supports Java +6, Linux, Mac, Windows

#### Useful links

- [Javadoc](https://dialex.github.io/JCDP/)
- [Changelog](https://github.com/dialex/JCDP/releases/)

### License

JCDP  Copyright (C) 2011-*  [Diogo Nunes](http://www.diogonunes.com/)
This program is licensed under the terms of the MIT License and it comes with ABSOLUTELY NO WARRANTY. For more details check LICENSE.

### Credits

A big thanks to all [contributors](https://github.com/dialex/JCDP/graphs/contributors), specially [@xafero](https://github.com/xafero) who _maven-ized_ this whole project.
