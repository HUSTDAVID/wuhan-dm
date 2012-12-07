
package com.wh.dm.receiver;

import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.activity.MessageActivity;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.preference.Preferences;
import com.wh.dm.type.PostMessage;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;

public class AlarmReceiver extends BroadcastReceiver {

    private FetchDataTask fetchDataTask;
    private NotificationManager notificationManager;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    private int msgNum;
    private Context _context;

    @Override
    public void onReceive(Context context, Intent intent) {

        WH_DMApp app = (WH_DMApp) context.getApplicationContext();
        _context = context;
        databaseImpl = app.getDatabase();
        wh_dmApi = app.getWH_DMApi();
        msgNum = Preferences.getMsgNum(context);
        getData();
    }

    public void showNotification(Context context, PostMessage message, int flag) {

        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.icon = R.drawable.push;
        notification.tickerText = message.getTitle();
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent_push;
        // if (message.getTemp() == 0) {
        intent_push = new Intent(context, MessageActivity.class);
        // } else {
        // intent_push = new Intent(context, DM_MZineArticleActivity.class);
        // }
        // Bundle bundle = new Bundle();
        // bundle.putInt("sid", message.getMid());
        // intent_push.putExtras(bundle);
        PendingIntent sender = PendingIntent.getActivity(context, flag, intent_push, flag);

        notification.contentIntent = sender;
        String magazine_name = message.getMname();
        String content = message.getTitle();
        notification.setLatestEventInfo(context, magazine_name, content, sender);
        nm.notify(flag, notification);

    }

    private void getData() {

        if (fetchDataTask != null) {
            fetchDataTask.cancel(true);
            fetchDataTask = null;
        }
        fetchDataTask = new FetchDataTask();
        fetchDataTask.execute();

    }

    private class FetchDataTask extends AsyncTask<Void, Void, ArrayList<PostMessage>> {
        Exception reason = null;

        @Override
        protected ArrayList<PostMessage> doInBackground(Void... params) {

            try {
                ArrayList<PostMessage> messages = wh_dmApi.getMessage();
                return messages;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<PostMessage> result) {

            ArrayList<PostMessage> newMessages = new ArrayList<PostMessage>();
            if (result != null) {
                if (msgNum < result.size()) {
                    int size = result.size();
                    for (int i = msgNum; i < size; i++) {
                        // showNotification(_context, result.get(i), i);
                        PostMessage message = result.get(i);
                        message.setIsRead(0);
                        newMessages.add(message);
                    }
                    databaseImpl.addPostMessage(newMessages);
                    Preferences.setPostMessage(_context, size);
                }
            }
            super.onPostExecute(result);
        }

    }

}
