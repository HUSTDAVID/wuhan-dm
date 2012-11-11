
package com.wh.dm.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Preferences {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static void saveUser(Context context, String email, String password) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

}
