
package com.wh.dm;

import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.Result;
import com.wh.dm.util.SettingUtil;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.PowerManager.WakeLock;

public class WH_DMApp extends Application {

    public static Context mContext;
    WakeLock wakeLock = null;

    private WH_DMApi wh_dm;
    private DatabaseImpl databaseImpl;
    private SharedPreferences mPrefs;
    private boolean isLogin = false;
    private boolean isConnected = false;
    private final boolean isSinaLogin = false;
    private final boolean isTencLogin = false;

    private static final String INTENT_ACTION_LOGGED_IN = "com.wh.dm.intent.action.LOGGED_IN";
    private static final String INTENT_ACTION_LOGGED_OUT = "com.wh.dm.intent.action.LOGGED_OUT";

    @Override
    public void onCreate() {

        super.onCreate();
        loadWH_DM();
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

    public void setConnected(boolean _isConnected) {

        isConnected = _isConnected;
    }

    private class LoginTask extends AsyncTask<String, Void, Result> {

        @Override
        protected Result doInBackground(String... params) {

            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected void onPostExecute(Result result) {

            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }

    }

    // two method for wake lock
    public void acquireWakeLock() {

        wakeLock = SettingUtil.setAcquireWakeLock(mContext, wakeLock);
    }

    public void releaseWakeLock() {

        wakeLock = SettingUtil.setReleaseWakeLock(wakeLock);
    }

}
