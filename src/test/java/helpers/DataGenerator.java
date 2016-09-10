package helpers;

import java.text.DateFormat;
import java.util.Date;

public class DataGenerator {

    public static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd HH:mm:ss";

    public static String getNormalMsg() {
        return "Normal";
    }

    public static String getDate(DateFormat formatter) {
        return formatter.format(new Date());
    }

    public static String getDate() {
        return new Date().toString();
    }
}
