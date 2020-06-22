package com.diogonunes.jcolor.tests.unit;

public class DataGenerator {

    public static final String NEWLINE = System.getProperty("line.separator");

    public static String createText() {
        return "Message";
    }

    public static String createTextWithId(int n) {
        return "Message" + n;
    }

    public static String createTextLine() {
        return "This is a line" + NEWLINE;
    }
}
