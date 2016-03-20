package com.example.beach.kopie.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;

import static com.example.beach.kopie.util.Constants.KOPIE;
import static com.example.beach.kopie.util.Constants.KOPIE_ENABLED;

public class SharedPreferenceService {

    public static boolean isKopieEnabled(Context context) {
        return getSharedPreference(context).getBoolean(KOPIE_ENABLED, false);
    }

    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(KOPIE, Context.MODE_PRIVATE);
    }

    public static void setKopieEnabled(Context context, boolean enabled) {
        getSharedPreference(context)
                .edit()
                .putBoolean(KOPIE_ENABLED, enabled)
                .commit();
    }

    public static void saveKopieLocation(Context context, Point point) {
        getSharedPreference(context)
                .edit()
                .putInt("stateX", point.x)
                .putInt("stateY", point.y)
                .commit();
    }

    public static Point getKopieLocation(Context context, Point point) {
        int stateX = getSharedPreference(context).getInt("stateX", point.x);
        int stateY = getSharedPreference(context).getInt("stateY", point.y);
        return new Point(stateX, stateY);
    }
}
