
package com.wh.dm.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class SettingUtil {

    public static WakeLock setAcquireWakeLock(Context context, WakeLock wakeLock) {

        if (wakeLock == null) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Wake_Lock");
            wakeLock.acquire();
        }
        return wakeLock;

    }

    public static WakeLock setReleaseWakeLock(WakeLock wakeLock) {

        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }
        return wakeLock;

    }

    public static boolean isDownloadImg(SharedPreferences pref, Context context) {

        String key = pref.getString("flow", "key1");
        if (key.equals("key0")) {
            return ConnetivityUtil.isWifi(context);
        } else if (key.equals("key1")) {
            return true;
        } else {
            return false;
        }

    }

}
