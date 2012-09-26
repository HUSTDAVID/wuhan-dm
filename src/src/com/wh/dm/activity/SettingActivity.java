
package com.wh.dm.activity;

import com.wh.dm.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preference);
    }
}
