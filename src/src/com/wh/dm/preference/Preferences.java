
package com.wh.dm.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

public class Preferences {

    public static final String FISRT_LAUNCH = "first_launch";
    public static final String DEVICE_ID = "device_id";

    public static final String GET_DETAULT_MAGAZIE = "get_detault_magazine";

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static final String NEWS_ALL_SORT = "news_all_sort";
    public static final String NEWS_ALL_ID = "news_all_id";

    public static final String PHOTO_ALL_SORT = "photo_all_sort";
    public static final String PHOTO_ALL_ID = "photo_all_id";

    public static final String MAGAZINE_ALL_SORT = "magazine_all_sort";
    public static final String MAGAZINE_ALL_ID = "magazine_all_id";

    public static final String LOAD_PIC_PATH = "pic_path";
    public static final String POST_MESSAGE = "post_message";

    public static final String UPDATE_DATABASE = "update_database_v3";

    public static void firstLaunch(Context context, String deviceId) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(DEVICE_ID, deviceId);
        editor.putBoolean(FISRT_LAUNCH, false);
        editor.commit();
    }

    public static void getDefalutMagazine(Context context) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putBoolean(GET_DETAULT_MAGAZIE, true);
        editor.commit();
    }

    public static void saveUser(Context context, String email, String password) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public static void logout(Context context) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(EMAIL, "");
        editor.putString(PASSWORD, "");
        editor.commit();
    }

    public static void saveNewsType(Context context, String newsAllSort, String newsAllId) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(NEWS_ALL_SORT, newsAllSort);
        editor.putString(NEWS_ALL_ID, newsAllId);
        editor.commit();

    }

    public static void savePhotoType(Context context, String photoAllSort, String photoAllId) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(PHOTO_ALL_SORT, photoAllSort);
        editor.putString(PHOTO_ALL_ID, photoAllId);
        editor.commit();

    }

    public static void saveMagazienType(Context context, String magazineAllSort,
            String magazineAllId) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(MAGAZINE_ALL_SORT, magazineAllSort);
        editor.putString(MAGAZINE_ALL_ID, magazineAllId);
        editor.commit();
    }

    public static void saveLoadPic(Context context, String pic) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(LOAD_PIC_PATH, pic);
        editor.commit();
    }

    public static String getLoadPic(Context context) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        return preference.getString(LOAD_PIC_PATH, "");
    }

    public static void setPostMessage(Context context, int num) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putInt(POST_MESSAGE, num);
        editor.commit();
    }

    public static int getMsgNum(Context context) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        return preference.getInt(POST_MESSAGE, 0);
    }

    public static void setUpdateDB(Context context) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putBoolean(UPDATE_DATABASE, false);
        editor.commit();
    }

    public static String getMachineId(Context context) {

        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
        String machineId = spf.getString(DEVICE_ID, "");
        if (machineId == null || machineId.equals("")) {
            final TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            machineId = "" + tm.getDeviceId();
        }

        return machineId;
    }

}
