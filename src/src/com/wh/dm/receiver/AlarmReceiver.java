
package com.wh.dm.receiver;

import com.wh.dm.R;
import com.wh.dm.activity.DM_Tab_1Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

public class AlarmReceiver extends BroadcastReceiver {

    private FetchDataTask fetchDataTask;
    private NotificationManager notificationManager;
    private SharedPreferences sp;

    @Override
    public void onReceive(Context context, Intent intent) {

        showNotification(context);
        sp = context.getSharedPreferences("msgNum", 0);
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

    private void getData(Context context) {

        if (fetchDataTask != null) {
            fetchDataTask.cancel(true);
            fetchDataTask = null;
        }
        fetchDataTask = new FetchDataTask();
        fetchDataTask.execute();

    }

    private boolean isFetched() {

        return false;
    }

    private class FetchDataTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            super.onPostExecute(result);
        }

    }

}
