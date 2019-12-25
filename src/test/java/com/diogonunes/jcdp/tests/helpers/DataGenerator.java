package com.diogonunes.jcdp.tests.helpers;

import java.text.DateFormat;
import java.util.Date;

public class DataGenerator {

    public static final String NEWLINE = System.getProperty("line.separator");
    public static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd HH:mm:ss";

    //TODO rename to createText
    public static String createMsg() {
        return "Message";
    }

    public static String createMsgWithId(int n) {
        return "Message" + n;
    }

    public static String createErrorMsg() {
        return "Error";
    }

    public static String createMsgLine() {
        return "Single line" + NEWLINE;
    }

    public static String getCurrentDate(DateFormat formatter) {
        return formatter.format(new Date());
    }

    public static String getCurrentDate() {
        return new Date().toString();
    }

    public static String createSeparator() {
        return " // ";
    }
}
