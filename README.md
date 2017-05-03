<img src="https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/JCDP-logo.png" width="150">

[![Travis Build Status](https://img.shields.io/travis/dialex/JCDP.svg?maxAge=2592000)](https://travis-ci.org/dialex/JCDP) [![Codacy grade](https://img.shields.io/codacy/grade/faaed58a577d4c3099cf8d6d4d572fb8.svg?maxAge=2592000)]() [![Version](https://img.shields.io/github/tag/dialex/JCDP.svg)](https://github.com/dialex/JCDP#build-tools) [![License](https://img.shields.io/github/license/dialex/JCDP.svg?maxAge=2592000)](https://github.com/dialex/JCDP/blob/master/LICENSE)

**Java Colored Debug Printer** (JCDP) offers you an easy syntax to print messages with a colored font or background on a terminal. It also provides custom levels of debug logging.

### Screenshots

![Ubuntu screenshot](https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/ubuntu-console.png)

*Running on Ubuntu*

![MacIterm screenshot](https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/mac-iterm.png)

*Running on MacOS X Yosemite (iTerm)*

![Win8cmd screenshot](https://raw.githubusercontent.com/dialex/JCDP/master/doc/img/win8-cmd.png)

*Running on Windows 8.1 (cmd)*

### Example

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
cp.print("IN PORTUGAL\t\n", Attribute.BOLD, FColor.YELLOW, BColor.RED);
cp.println("I hope you find it useful ;)");

cp.clear(); //don't forget to clear the terminal's format before exiting
```

### Build tools

You can import this library through Maven or Gradle, just [pick a version](https://mvnrepository.com/artifact/com.diogonunes/JCDP).

### Documentation

[Javadoc](http://dialex.github.io/JCDP/javadoc/)

[Changelog](changelog.md)

### License

JCDP  Copyright (C) 2011-*  [Diogo Nunes](http://www.diogonunes.com/)
This program is licensed under the terms of the MIT License and it comes with ABSOLUTELY NO WARRANTY. For more details check LICENSE.

A special thanks to all contributors, specially [@xafero](https://github.com/xafero) who _maven-ized_ this whole project.
