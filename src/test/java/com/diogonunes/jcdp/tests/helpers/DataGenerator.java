package com.diogonunes.jcdp.tests.helpers;

import java.text.DateFormat;
import java.util.Date;

public class DataGenerator {

    public static final String NEWLINE = System.getProperty("line.separator");
    public static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd HH:mm:ss";

    public static String createText() {
        return "Message";
    }

    public static String createTextWithId(int n) {
        return "Message" + n;
    }

    public static String createTextLine() {
        return "This is a line" + NEWLINE;
    }

    public static String getCurrentDate(DateFormat formatter) {
        return formatter.format(new Date());
    }

    public static String createSeparator() {
        return " // ";
    }
}
