
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.preference.Preferences;
import com.wh.dm.service.PushService;
import com.wh.dm.type.PostResult;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.UrlImageViewHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
                    Preferences.saveLoadPic(SplashActivity.this, result.getMsg());
                }
            } else {
                NotificationUtil.showShortToast(reason.toString(), SplashActivity.this);
            }
            super.onPostExecute(result);
        }

    }

}
