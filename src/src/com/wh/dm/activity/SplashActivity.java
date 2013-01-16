
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.preference.Preferences;
import com.wh.dm.service.PushService;
import com.wh.dm.type.PostResult;
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
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstLaunch = preference.getBoolean(Preferences.FISRT_LAUNCH, true);

        String path = Preferences.getLoadPic(SplashActivity.this);

        if (firstLaunch) {
            img.setBackgroundDrawable(getResources().getDrawable(R.drawable.splash));
        } else {
            UrlImageViewHelper.setUrlDrawable(img, WH_DMHttpApiV1.URL_DOMAIN + path,
                    R.drawable.splash, null);
        }

        if (firstLaunch) {
            final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(
                    Context.TELEPHONY_SERVICE);
            String tmDevice = "" + tm.getDeviceId();
            Preferences.firstLaunch(this, tmDevice);
        }

        GetLoadPicTask getLoadPictask = new GetLoadPicTask();
        getLoadPictask.execute();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 1500);
        // push need to login first
        startService(new Intent(SplashActivity.this, PushService.class));
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
                    UrlImageViewHelper.setUrlDrawable(new ImageView(SplashActivity.this),
                            WH_DMHttpApiV1.URL_DOMAIN + result.getMsg(), R.drawable.splash, null);
                    Preferences.saveLoadPic(SplashActivity.this, result.getMsg());
                }
            }
            super.onPostExecute(result);
        }

    }

}
