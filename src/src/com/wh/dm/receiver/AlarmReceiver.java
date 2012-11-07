
package com.wh.dm.receiver;

import com.wh.dm.service.PushService;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String FEATCH = "fetch";
    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isFetch = intent.getBooleanExtra(FEATCH, true);
        if (isFetch) {
            // getData();

        } else {
            Intent mintent = new Intent(context, PushService.class);
            context.stopService(mintent);
        }
    }

    public void showNotification() {

    }

    private void getData() {

    }

}
