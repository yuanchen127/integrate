package org.ike.integrate.tool;

import java.math.BigDecimal;
import java.util.Date;

public class CSVUtil {
    public static String EMPTY = "";
    public static String toCSV(String value) {
        if (null == value) {
            return EMPTY;
        }
        return String.format("\"%s\"", value);
    }

    public static String toCSV(Date value) {
        if (null == value) {
            return EMPTY;
        }
        return  String.format("\"%s\"", value);
    }

    public static String toCSV(Integer value) {
        if (null == value)
            return EMPTY;
        return String.format("%s", value);
    }

    public static String toCSV(boolean value) {
        return String.valueOf(value);
    }

    public static String toCSV(BigDecimal value) {
        if (null == value)
            return EMPTY;
        return String.valueOf(value);
    }

    public static void main(String[] args) {
        System.out.println("qwe");
        System.out.println(toCSV("asd"));
    }
}
