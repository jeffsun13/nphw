package app.com.example.victoriajuan.nphshomework;

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

    public static boolean getMornNot(Context ctx) { return getSharedPreferences(ctx).getBoolean(MORN_NOT, true); }
    public static boolean getNightNot(Context ctx) { return getSharedPreferences(ctx).getBoolean(NIGHT_NOT, true); }
}