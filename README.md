# <img src="https://raw.githubusercontent.com/dialex/JColor/main/.github/img/JColor-logo.png" width="150">

[![Travis build](https://img.shields.io/travis/dialex/JCDP)](https://travis-ci.org/dialex/JCDP) [![Maven Central](https://img.shields.io/maven-central/v/com.diogonunes/JColor)](https://mvnrepository.com/artifact/com.diogonunes/JColor) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/faaed58a577d4c3099cf8d6d4d572fb8)](https://www.codacy.com/manual/dialex-nunes/JColor?utm_source=github.com&utm_medium=referral&utm_content=dialex/JColor&utm_campaign=Badge_Grade) [![License](https://img.shields.io/github/license/dialex/JColor.svg)](https://github.com/dialex/JColor/blob/main/LICENSE) [![Donate](https://img.shields.io/badge/donate-%E2%99%A5%20-E91E63.svg)](https://www.paypal.me/dialexnunes/)

**JColor** (formerly Java Colored Debug Printer) offers you an easy syntax to print messages with a colored font or background on a terminal.

### Screenshots

![JColor running on iTerm (macOS)](https://raw.githubusercontent.com/dialex/JColor/main/.github/img/example-mac-iterm-fancy.png)

How it looks on different platforms: [macOS iTerm](https://raw.githubusercontent.com/dialex/JColor/main/.github/img/example-mac-iterm-fancy.png), [Windows Terminal](https://raw.githubusercontent.com/dialex/JColor/main/.github/img/example-win-console.png), [IntelliJ](https://raw.githubusercontent.com/dialex/JColor/main/.github/img/example-IntelliJ.png)

### Usage

The screenshot was the result of running this demo code:

```java
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
    Attribute txtColor = TEXT_COLOR(i);
    System.out.print(colorize(String.format("%4d", i), txtColor));
}
System.out.println("\n");

// Use Case 7: we support true colors (RGB)
System.out.println("Any TrueColor (RGB), as long as your terminal supports it:");
for (int i = 0; i <= 300; i++) {
    Attribute bkgColor = BACK_COLOR(randomInt(255), randomInt(255), randomInt(255));
    System.out.print(colorize("   ", bkgColor));
}
System.out.println("\n");

// Credits
System.out.print("This example used JColor 5.0.0   ");
System.out.print(colorize("\tMADE ", BOLD(), BRIGHT_YELLOW_TEXT(), GREEN_BACK()));
System.out.println(colorize("IN PORTUGAL\t", BOLD(), BRIGHT_YELLOW_TEXT(), RED_BACK()));
System.out.println("I hope you find it useful ;)");
```

#### Installation

You can import this dependency through Maven or Gradle:

- [JColor](https://mvnrepository.com/artifact/com.diogonunes/JColor) `v5.*` supports Java +8, Linux, macOS, Windows* +10
- [JCDP](https://mvnrepository.com/artifact/com.diogonunes/JCDP) `v4.*` supports Java +8, Linux, macOS, Windows* +10
- JCDP `v3.*` supports Java +8, Linux, macOS, Windows
- JCDP `v2.*` supports Java +6, Linux, macOS, Windows

> ⚠️ *Windows users, attention!
>
> Even though Windows 10 has support for ANSI escape sequences, it is [disabled by default](https://stackoverflow.com/questions/51680709/colored-text-output-in-powershell-console-using-ansi-vt100-codes/51681675#51681675). The easiest way to fix this is to use [Windows Terminal](https://www.microsoft.com/en-us/p/windows-terminal/9n0dx20hk701), otherwise pick one of these [workarounds](https://github.com/dialex/JColor/issues/62#issuecomment-967010670).

#### Useful links

- [Javadoc](https://dialex.github.io/JColor/)
- [Changelog](https://github.com/dialex/JColor/releases/)

### License

JColor, former JCDP Copyright (C) 2011-\* [Diogo Nunes](https://www.diogonunes.com/)
This program is licensed under the terms of the MIT License and it comes with ABSOLUTELY NO WARRANTY. For more details check LICENSE.

### Credits

A big thanks to all [contributors](https://github.com/dialex/JColor/graphs/contributors), namely [@xafero](https://github.com/xafero) who _maven-ized_ this project.
