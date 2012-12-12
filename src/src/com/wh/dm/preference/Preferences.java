
package com.wh.dm.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Preferences {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static final String NEWS_ONE = "news_one";
    public static final String NEWS_TWO = "news_two";
    public static final String NEWS_THREE = "news_three";
    public static final String NEWS_FOUR = "news_four";
    public static final String NEWS_FIVE = "news_five";

    public static final String NEWS_ONE_ID = "id_news_one";
    public static final String NEWS_TWO_ID = "id_news_two";
    public static final String NEWS_THREE_ID = "id_news_three";
    public static final String NEWS_FOUR_ID = "id_news_four";
    public static final String NEWS_FIVE_ID = "id_news_five";

    public static final String PHOTO_ONE = "photo_one";
    public static final String PHOTO_TWO = "photo_two";
    public static final String PHOTO_THREE = "photo_three";
    public static final String PHOTO_FOUR = "photo_four";

    public static final String PHOTO_ONE_ID = "id_photo_one";
    public static final String PHOTO_TWO_ID = "id_photo_two";
    public static final String PHOTO_THREE_ID = "id_photo_three";
    public static final String PHOTO_FOUR_ID = "id_photo_four";

    public static final String MAGAZINE_ONE = "magazien_one";
    public static final String MAGAZINE_TWO = "magazien_two";
    public static final String MAGAZINE_THREE = "magazien_three";
    public static final String MAGAZINE_FOUR = "magazien_four";

    public static final String MAGAZINE_ONE_ID = "id_magazien_one";
    public static final String MAGAZINE_TWO_ID = "id_magazien_two";
    public static final String MAGAZINE_THREE_ID = "id_magazien_three";
    public static final String MAGAZINE_FOUR_ID = "id_magazien_four";

    public static final String LOAD_PIC_PATH = "pic_path";
    public static final String POST_MESSAGE = "post_message";

    public static final String UPDATE_DATABASE = "update_database";

    public static void saveUser(Context context, String email, String password) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public static void saveNewsType(Context context, String one, String two, String three,
            String four, String five, int idOne, int idTwo, int idThree, int idFour, int idFive) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(NEWS_ONE, one);
        editor.putString(NEWS_TWO, two);
        editor.putString(NEWS_THREE, three);
        editor.putString(NEWS_FOUR, four);
        editor.putString(NEWS_FIVE, five);
        editor.putInt(NEWS_ONE_ID, idOne);
        editor.putInt(NEWS_TWO_ID, idTwo);
        editor.putInt(NEWS_THREE_ID, idThree);
        editor.putInt(NEWS_FOUR_ID, idFour);
        editor.putInt(NEWS_FIVE_ID, idFive);
        editor.commit();

    }

    public static void savePhotoType(Context context, String one, String two, String three,
            String four, int idOne, int idTwo, int idThree, int idFour) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(PHOTO_ONE, one);
        editor.putString(PHOTO_TWO, two);
        editor.putString(PHOTO_THREE, three);
        editor.putString(PHOTO_FOUR, four);
        editor.putInt(PHOTO_ONE_ID, idOne);
        editor.putInt(PHOTO_TWO_ID, idTwo);
        editor.putInt(PHOTO_THREE_ID, idThree);
        editor.putInt(PHOTO_FOUR_ID, idFour);
        editor.commit();

    }

    public static void saveMagazienType(Context context, String one, String two, String three,
            String four, int idOne, int idTwo, int idThree, int idFour) {

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preference.edit();
        editor.putString(MAGAZINE_ONE, one);
        editor.putString(MAGAZINE_TWO, two);
        editor.putString(MAGAZINE_THREE, three);
        editor.putString(MAGAZINE_FOUR, four);
        editor.putInt(MAGAZINE_ONE_ID, idOne);
        editor.putInt(MAGAZINE_TWO_ID, idTwo);
        editor.putInt(MAGAZINE_THREE_ID, idThree);
        editor.putInt(MAGAZINE_FOUR_ID, idFour);
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

}
