package app.com.example.victoriajuan.nphshomework;

import android.app.Application;

/**
 * Created by Jeffrey Sun on 9/18/2016.
 */
public class GlobalVariables extends Application {

    private static int DAY;
    private static int MONTH;
    private static int YEAR;
    private static String CLASS_TYPE;
    private static int ICON_TYPE;
    private static String CLASS_DETAILS;

    public static int getDay() {
        return DAY;
    }

    public static int getMonth() {
        return MONTH;
    }

    public static int getYear() {
        return YEAR;
    }

    public static String getClassType() { return CLASS_TYPE; }

    public static int getIconType() { return ICON_TYPE; }

    public static String getClassDetails() { return CLASS_DETAILS; }

    public static void setDate(int day, int month, int year) {
        DAY = day;
        MONTH = month;
        YEAR = year;
    }

    public static void setDetailInfo(String str, int num, String details) {
        CLASS_TYPE = str;
        ICON_TYPE = num;
        CLASS_DETAILS = details;
    }
}
