
package com.wh.dm;

import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.service.PushService;
import com.wh.dm.util.SettingUtil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class WH_DMApp extends Application {

    public static Context mContext;
    WakeLock wakeLock = null;

    private WH_DMApi wh_dm;
    private DatabaseImpl databaseImpl;
    // private ArrayList<PostMessage> messages;
    private SharedPreferences mPrefs;
    public static boolean isLogin = false;
    public static boolean isConnected = true;
    public static boolean isSinaLogin = false;
    public static boolean isTencLogin = false;
    public static boolean isLoadImg;

    public static final String INTENT_ACTION_LOG_SUCCESS = "com.wh.dm.intent.action.LOG_SUCCESS";
    public static final String INTENT_ACTION_LOG_FAIL = "com.wh.dm.intent.action.LOG_FAIL";
    public static final String INTENT_ACTION_SUBCRIBE_CHANGE = "com.wh.dm.intent.action.change";

    @Override
    public void onCreate() {

        super.onCreate();
        loadWH_DM();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        isLoadImg = SettingUtil.isDownloadImg(mPrefs, this);
        login();

    }

    public WH_DMApi getWH_DMApi() {

        return wh_dm;
    }

    public DatabaseImpl getDatabase() {

        if (databaseImpl == null) {
            databaseImpl = new DatabaseImpl(this);
        }
        return databaseImpl;
    }

    private void loadWH_DM() {

        wh_dm = new WH_DMApi();
    }

    public boolean isLogin() {

        return isLogin;
    }

    public void setIsLogin(boolean _isLogin) {

        isLogin = _isLogin;
    }

    public boolean isConnected() {

        return isConnected;
    }

    public ArrayList<String> getUserInfo() {

        ArrayList<String> users = new ArrayList<String>();
        String email = mPrefs.getString("email", null);
        String password = mPrefs.getString("password", null);
        users.add(email);
        users.add(password);
        return users;
    }

    public void setConnected(boolean _isConnected) {

        isConnected = _isConnected;
    }

    // two method for wake lock
    public void acquireWakeLock() {

        wakeLock = SettingUtil.setAcquireWakeLock(mContext, wakeLock);
    }

    public void releaseWakeLock() {

        wakeLock = SettingUtil.setReleaseWakeLock(wakeLock);
    }

    private void login() {

        LoginTask loginTask = new LoginTask();
        ArrayList<String> users = getUserInfo();
        if (users.get(0) == null || users.get(1) == null) {
            return;
        } else {
            loginTask.execute(users.get(0), users.get(1));
        }
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            boolean login = false;
            try {
                login = wh_dm.login(params[0], params[1]);
                return login;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                Intent intent = new Intent(INTENT_ACTION_LOG_SUCCESS);
                sendBroadcast(intent);
                startService(new Intent(WH_DMApp.this, PushService.class));
                isLogin = true;
            } else {
                Intent intent = new Intent(INTENT_ACTION_LOG_FAIL);
                sendBroadcast(intent);
                isLogin = false;
            }
            super.onPostExecute(result);
        }

    }

}
