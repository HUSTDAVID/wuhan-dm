
package com.wh.dm.service;

import com.wh.dm.receiver.AlarmReceiver;
import com.wh.dm.util.TimeUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PushService extends Service {

    private AlarmManager alarmManager = null;
    private static final String FREQUENCE = "frequence";
    private static final String START_TIME = "8:00:00";
    private static final String END_TIME = "9:00:00";
    private static final String FETCH = "fetch";

    @Override
    public void onCreate() {

        alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);

    }

    @Override
    public void onStart(Intent intent, int startId) {

        int frequence = intent.getIntExtra(FREQUENCE, 1000);
        long startTime = TimeUtil.getCurtime(START_TIME);
        long endTime = TimeUtil.getCurtime(END_TIME);

        Intent mIntent1 = new Intent(this, AlarmReceiver.class);
        mIntent1.putExtra(FETCH, true);
        PendingIntent sender1 = PendingIntent.getBroadcast(this, 1, mIntent1, 0);
        Intent mIntent2 = new Intent(this, AlarmReceiver.class);
        mIntent2.putExtra(FETCH, false);
        PendingIntent sender2 = PendingIntent.getBroadcast(this, 2, mIntent2, 0);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, startTime, frequence,
                sender1);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, endTime, sender2);

    }

    @Override
    public void onDestroy() {

        Intent mIntent1 = new Intent(this, AlarmReceiver.class);
        PendingIntent sender1 = PendingIntent.getBroadcast(this, 0, mIntent1, 0);
        alarmManager.cancel(sender1);
        Intent mIntent2 = new Intent(this, AlarmReceiver.class);
        PendingIntent sender2 = PendingIntent.getBroadcast(this, 0, mIntent2, 0);
        alarmManager.cancel(sender2);
        alarmManager = null;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {

        // TODO Auto-generated method stub
        return null;
    }

}
