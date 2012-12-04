
package com.wh.dm.service;

import com.wh.dm.receiver.AlarmReceiver;
import com.wh.dm.receiver.ClearReceiver;
import com.wh.dm.util.TimeUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PushService extends Service {

    private AlarmManager alarmManager = null;
    private static final int Frequence_Min = 5 * 60 * 1000;
    // private static final int Frequence_Min = 5 * 1000;
    private static final int Frequence_Day = 24 * 60 * 60 * 1000;
    // private static final int Frequence_Day = 5 * 1000;
    private static final String FETCH_START_TIME = "07:00:00";
    private static final String CLEAR_START_TIME = "06:00:00";
    private static final String FETCH = "fetch";
    private Intent intent_fetch;
    private PendingIntent sender1;
    private Intent intent_clear;
    private PendingIntent sender2;

    @Override
    public void onCreate() {

        alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);

    }

    @Override
    public void onStart(Intent intent, int startId) {

        intent_fetch = new Intent(this, AlarmReceiver.class);
        sender1 = PendingIntent.getBroadcast(this, 0, intent_fetch, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                TimeUtil.getCurtime(FETCH_START_TIME), Frequence_Min, sender1);
        // alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
        // System.currentTimeMillis(),
        // Frequence_Min, sender1);
        intent_clear = new Intent(this, ClearReceiver.class);
        sender2 = PendingIntent.getBroadcast(this, 1, intent_clear, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                TimeUtil.getCurtime(CLEAR_START_TIME), Frequence_Day, sender2);
    }

    @Override
    public void onDestroy() {

        alarmManager.cancel(sender1);
        alarmManager.cancel(sender2);
        alarmManager = null;
    }

    @Override
    public IBinder onBind(Intent intent) {

        // TODO Auto-generated method stub
        return null;
    }

}
