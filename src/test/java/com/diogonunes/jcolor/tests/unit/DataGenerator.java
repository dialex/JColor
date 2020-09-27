package com.diogonunes.jcolor.tests.unit;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataGenerator {

    public static final String NEWLINE = System.getProperty("line.separator");
    private static final Random _rand = new Random();

    public static String createText() {
        return "Message";
    }

    public static String createTextWithId(int n) {
        return "Message" + n;
    }

    public static String createTextLine() {
        return "This is a line" + NEWLINE;
    }

    public static int randomInt(int upperLimit) {
        return _rand.nextInt(upperLimit);
    }

    // Adapted from https://stackoverflow.com/a/2850495/675577
    public static int countLines(String text) {
        Matcher m = Pattern.compile(NEWLINE).matcher(text);
        int lines = 0;
        while (m.find())
            lines++;
        return lines;
    }
}
