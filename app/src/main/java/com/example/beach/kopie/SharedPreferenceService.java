package com.example.beach.kopie;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.beach.kopie.Constants.KOPIE;
import static com.example.beach.kopie.Constants.KOPIE_ENABLED;

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
}
