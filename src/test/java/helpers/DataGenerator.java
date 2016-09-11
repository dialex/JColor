package helpers;

import java.text.DateFormat;
import java.util.Date;

public class DataGenerator {

    public static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd HH:mm:ss";

    public static String createMsg() {
        return "Normal";
    }

    public static String createMsgWithId(int n) {
        return "Debug" + n;
    }

    public static String createErrorMsg() {
        return "Error";
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
