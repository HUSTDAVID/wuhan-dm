
package com.wh.dm.receiver;

import com.wh.dm.WH_DMApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectityReceiver extends BroadcastReceiver {

    private boolean isConnected;

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            isConnected = activeNetwork.isConnectedOrConnecting();
        } else {
            isConnected = false;
        }
        ((WH_DMApp) context.getApplicationContext()).setConnected(isConnected);

    }

}
