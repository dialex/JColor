# <img src="https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/JCDP-logo.png" width="150">

[![Travis build](https://img.shields.io/travis/dialex/jcdp)](https://travis-ci.org/dialex/JCDP) [![Maven Central](https://img.shields.io/maven-central/v/com.diogonunes/JCDP)](https://mvnrepository.com/artifact/com.diogonunes/JCDP) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/faaed58a577d4c3099cf8d6d4d572fb8)](https://www.codacy.com/app/dialex-nunes/JCDP?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dialex/JCDP&amp;utm_campaign=Badge_Grade) [![License](https://img.shields.io/github/license/dialex/JCDP.svg)](https://github.com/dialex/JCDP/blob/master/LICENSE) [![Donate](https://img.shields.io/badge/donate-%E2%99%A5%20-E91E63.svg)](https://www.paypal.me/dialexnunes/)

**Java Colored Debug Printer** (JCDP) offers you an easy syntax to print messages with a colored font or background on a terminal. It also provides custom levels of debug logging.

### Screenshots

![Ubuntu screenshot](https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/ubuntu-console.png)

*Running on Ubuntu*

![MacIterm screenshot](https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/mac-iterm.png)

*Running on MacOS X Yosemite (iTerm)*

![Win8cmd screenshot](https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/win8-cmd.png)

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

ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                        .foreground(FColor.WHITE).background(BColor.BLUE)   //setting format
                        .build();

//printing according to that format
cp.println(cp);
cp.setAttribute(Attribute.REVERSE);
cp.println("This is a normal message (with format reversed).");

//resetting the terminal to its default colors
cp.clear();
cp.print(cp.getDateFormatted(), Attribute.NONE, FColor.CYAN, BColor.BLACK);
cp.debugPrintln(" This debug message is always printed.");
cp.clear();
cp.print("This example used JCDP 1.25   ");

//temporarily overriding that format
cp.print("\tMADE ", Attribute.BOLD, FColor.YELLOW, BColor.GREEN);
cp.println("IN PORTUGAL", Attribute.BOLD, FColor.YELLOW, BColor.RED);
cp.println("I hope you find it useful ;)");

cp.clear(); //don't forget to clear the terminal's format before exiting
```

### Installation

You can import this dependency through Maven or Gradle, just [pick a version](https://mvnrepository.com/artifact/com.diogonunes/JCDP).

#### Requirements

- JCDP `v3.*` (latest) supports Java +8, Linux, Mac, Windows
- JCDP `v2.*` (legacy) supports Java +6, Linux, Mac, Windows

#### Useful links

- [Javadoc](http://dialex.github.io/JCDP/javadoc/)
- [Changelog](https://github.com/dialex/JCDP/releases/)

### License

JCDP  Copyright (C) 2011-*  [Diogo Nunes](http://www.diogonunes.com/)
This program is licensed under the terms of the MIT License and it comes with ABSOLUTELY NO WARRANTY. For more details check LICENSE.

### Credits

A big thanks to all [contributors](https://github.com/dialex/JCDP/graphs/contributors), specially [@xafero](https://github.com/xafero) who _maven-ized_ this whole project.
