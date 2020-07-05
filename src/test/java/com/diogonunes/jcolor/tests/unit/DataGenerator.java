package com.diogonunes.jcolor.tests.unit;

import java.util.Random;

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
}
