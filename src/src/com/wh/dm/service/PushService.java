
package com.wh.dm.service;

import com.wh.dm.receiver.AlarmReceiver;
import com.wh.dm.receiver.ClearDatabaseReceiver;
import com.wh.dm.util.TimeUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PushService extends Service {

    private AlarmManager alarmManager = null;
    private static final int Frequence_Min = 5 * 60 * 1000 * 1000;
    private static final int Frequence_Day = 24 * 60 * 60 * 1000 * 1000;
    private static final String START_TIME = "7:00:00";
    private static final String FETCH = "fetch";

    @Override
    public void onCreate() {

        alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);

    }

    @Override
    public void onStart(Intent intent, int startId) {

        Intent intent_fetch = new Intent(this, AlarmReceiver.class);
        PendingIntent sender1 = PendingIntent.getBroadcast(this, 0, intent_fetch, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, TimeUtil.getCurtime(START_TIME),
                Frequence_Min, sender1);
        Intent intent_clear = new Intent(this, ClearDatabaseReceiver.class);
        PendingIntent sender2 = PendingIntent.getBroadcast(this, 1, intent_clear, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, TimeUtil.getCurtime(START_TIME),
                Frequence_Day, sender2);
    }

    @Override
    public void onDestroy() {

        Intent intent_fetch = new Intent(this, AlarmReceiver.class);
        PendingIntent sender1 = PendingIntent.getBroadcast(this, 0, intent_fetch, 0);
        alarmManager.cancel(sender1);
        Intent intent_clear = new Intent(this, ClearDatabaseReceiver.class);
        PendingIntent sender2 = PendingIntent.getBroadcast(this, 1, intent_clear, 0);
        alarmManager.cancel(sender2);
        alarmManager = null;
    }

    @Override
    public IBinder onBind(Intent intent) {

        // TODO Auto-generated method stub
        return null;
    }

}
