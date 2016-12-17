package app.com.example.victoriajuan.nphshomework;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_USER_NAME = "username";
    static final String DAY = "balls"; //ok jeff
    static final String MONTH = "ball";
    static final String YEAR = "nuts";
    static final String NIGHT_NOT = "";
    static final String MORN_NOT = "";
    static final String LOGIN_TOKEN = "";
    static final String CLASSES = "";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.apply();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }


    public static void setNotifications(Context ctx, String str, String val) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        if (str.equals("pref_night_notif_key"))
            editor.putBoolean(NIGHT_NOT, Boolean.parseBoolean(val));
        else
            editor.putBoolean(MORN_NOT, Boolean.parseBoolean(val));
        editor.apply();
    }

    public static void setLoginToken(Context ctx, String token) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        int tokenLoc = token.indexOf("token");
        editor.putString(LOGIN_TOKEN, token.substring(tokenLoc+8,token.length()-2));
        editor.apply();
    }

    public static String getLoginToken(Context ctx) {
        return getSharedPreferences(ctx).getString(LOGIN_TOKEN, "");
    }

    public static void setClasses(Context ctx, String classes) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CLASSES,classes);
        editor.apply();
    }

    public static String getClasses(Context ctx) {
        return getSharedPreferences(ctx).getString(CLASSES, "");
    }

    public static boolean getMornNot(Context ctx) { return getSharedPreferences(ctx).getBoolean(MORN_NOT, true); }
    public static boolean getNightNot(Context ctx) { return getSharedPreferences(ctx).getBoolean(NIGHT_NOT, true); }
}