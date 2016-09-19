package app.com.example.victoriajuan.nphshomework;

import android.app.Application;

/**
 * Created by Jeffrey Sun on 9/18/2016.
 */
public class GlobalVariables extends Application {

    private static int DAY;
    private static int MONTH;
    private static int YEAR;

    public static int getDay() {
        return DAY;
    }

    public static int getMonth() {
        return MONTH;
    }

    public static int getYear() {
        return YEAR;
    }

    public static void setDate(int day, int month, int year) {
        DAY=day;
        MONTH=month;
        YEAR=year;
    }
}
