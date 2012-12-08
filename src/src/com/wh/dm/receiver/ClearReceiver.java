
package com.wh.dm.receiver;

import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.preference.Preferences;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ClearReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Preferences.setPostMessage(context, 0);
        WH_DMApp app = (WH_DMApp) context.getApplicationContext();
        DatabaseImpl databaseImpl = app.getDatabase();
        databaseImpl.deletePostMessage();

    }
}
