
package com.wh.dm.receiver;

import com.wh.dm.R;
import com.wh.dm.activity.DM_Tab_1Activity;
import com.wh.dm.db.DatabaseImpl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ClearDatabaseReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        showNotification(context);

    }

    public void showNotification(Context context) {

        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.icon = R.drawable.push;
        notification.tickerText = "www";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent_push = new Intent(context, DM_Tab_1Activity.class);
        PendingIntent sender = PendingIntent.getActivity(context, 0, intent_push, 0);
        notification.contentIntent = sender;
        notification.setLatestEventInfo(context, "www", "www", sender);
        nm.notify(1, notification);

    }

    public void clearPushData(Context context) {

        DatabaseImpl databaseImpl = new DatabaseImpl(context);

    }

}
