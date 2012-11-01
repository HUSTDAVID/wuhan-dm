
package com.wh.dm;

import android.app.Application;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class WH_DMApp extends Application {

    public static Context mContext;
    WakeLock wakeLock = null;

    @Override
    public void onCreate() {

        // TODO Auto-generated method stub
        super.onCreate();
    }

    // two methdo for wakeLock
    public void acquireWakeLock() {

        if (wakeLock == null) {
            PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Wake_Lock");
            wakeLock.acquire();
        }
    }

    public void releaseWakeLock() {

        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }
    }

}
