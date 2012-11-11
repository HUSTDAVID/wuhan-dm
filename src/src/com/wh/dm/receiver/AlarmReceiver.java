
package com.wh.dm.receiver;

import com.wh.dm.R;
import com.wh.dm.activity.DM_Tab_1Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class AlarmReceiver extends BroadcastReceiver {

    private FetchDataTask fetchDataTask;
    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = new Notification(R.drawable.add_normal, "Hello,there!",
                System.currentTimeMillis());
        n.flags = Notification.FLAG_INSISTENT;
        Intent intent_push = new Intent(context, DM_Tab_1Activity.class);

    }

    public void showNotification() {

    }

    private void getData() {

        if (fetchDataTask != null) {
            fetchDataTask.cancel(true);
            fetchDataTask = null;
        }
        fetchDataTask = new FetchDataTask();
        fetchDataTask.execute();
    }

    private class FetchDataTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }

    }

}
