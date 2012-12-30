
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.preference.Preferences;
import com.wh.dm.type.PostResult;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.UrlImageViewHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.widget.ImageView;

public class SplashActivity extends Activity {
    private ImageView img;

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_splash);
        img = (ImageView) findViewById(R.id.loading);
        String path = Preferences.getLoadPic(SplashActivity.this);
        if (path != null && path.length() > 0) {
            UrlImageViewHelper.setUrlDrawable(img, WH_DMHttpApiV1.URL_DOMAIN + path,
                    R.drawable.splash, null);
        }

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstLaunch = preference.getBoolean(Preferences.FISRT_LAUNCH, true);
        if (firstLaunch) {
            final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(
                    Context.TELEPHONY_SERVICE);
            String tmDevice = "" + tm.getDeviceId();
            Preferences.firstLaunch(this, tmDevice);
        }
        /*
         * boolean update_db =
         * preference.getBoolean(Preferences.UPDATE_DATABASE, true); if
         * (update_db) { DatabaseImpl databaseImpl = ((WH_DMApp)
         * getApplication()).getDatabase(); databaseImpl.deleteMagazineBody();
         * databaseImpl.deleteLoadInfo(); Preferences.setUpdateDB(this); }
         */
        GetLoadPicTask getLoadPictask = new GetLoadPicTask();
        getLoadPictask.execute();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 1200);
        // push need to login first
        // startService(new Intent(SplashActivity.this, PushService.class));
    }

    private class GetLoadPicTask extends AsyncTask<Void, Void, PostResult> {

        Exception reason = null;

        @Override
        protected PostResult doInBackground(Void... params) {

            try {
                WH_DMApp app = (WH_DMApp) getApplication();
                PostResult result = app.getWH_DMApi().getLoadPic();
                return result;
            } catch (Exception e) {
                reason = e;
                return null;
            }

        }

        @Override
        protected void onPostExecute(PostResult result) {

            if (result != null) {
                if (result.getResult()) {
                    Preferences.saveLoadPic(SplashActivity.this, result.getMsg());
                }
            } else {
                NotificationUtil.showShortToast(getResources().getString(R.string.badconnect),
                        SplashActivity.this);
            }
            super.onPostExecute(result);
        }

    }

}
