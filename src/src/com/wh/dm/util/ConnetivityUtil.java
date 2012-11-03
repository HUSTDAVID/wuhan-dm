
package com.wh.dm.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class ConnetivityUtil {

    public static boolean isConnect(Context context) {

        ConnectivityManager mConnectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mTelephony = (TelephonyManager) context
                .getSystemService(context.TELEPHONY_SERVICE);
        // check if connect
        NetworkInfo netInfo = mConnectivity.getActiveNetworkInfo();
        if (netInfo == null || !mConnectivity.getBackgroundDataSetting()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isWifi(Context context) {

        ConnectivityManager mConnectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivity.getActiveNetworkInfo();

        if (netInfo == null || !mConnectivity.getBackgroundDataSetting()) {
            return false;
        } else if (ConnectivityManager.TYPE_WIFI == netInfo.getType()) {
            return netInfo.isConnected();
        } else {
            return false;
        }

    }

}
