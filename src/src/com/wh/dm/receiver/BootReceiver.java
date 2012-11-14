
package com.wh.dm.receiver;

import com.wh.dm.R;
import com.wh.dm.activity.DM_Tab_1Activity;
import com.wh.dm.util.NotificationUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    private final String action = "android.intent.action.MAIN";
    private final String category = "android.intent.category.LAUNCHER";
    private static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION)) {
            NotificationUtil.showLongToast("normal", context);
        }
        /*
         * Intent start = new Intent(context, SplashActivity.class);
         * start.setAction(action); start.addCategory(category);
         * start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         * context.startActivity(start);
         */
    }

    public void showNotification(Context context) {

        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.icon = R.drawable.push;
        notification.tickerText = "xxx";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent_push = new Intent(context, DM_Tab_1Activity.class);
        PendingIntent sender = PendingIntent.getActivity(context, 0, intent_push, 0);
        notification.contentIntent = sender;
        notification.setLatestEventInfo(context, "xxx", "xxx", sender);
        nm.notify(0, notification);

    }

}
