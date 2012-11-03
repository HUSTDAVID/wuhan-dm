
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class MoreActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener,
        OnPreferenceClickListener {

    TextView txt_title;
    ListPreference listPreference;
    SharedPreferences sPreference;
    WH_DMApp wh_app;

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preference_more);
        setContentView(R.layout.activity_setting);
        init();
    }

    @Override
    protected void onResume() {

        listPreference.setSummary(getString(R.string.current_text_size) + getTextSize());
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
                this);
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void init() {

        wh_app = (WH_DMApp) getApplication();
        Preference pre_light = findPreference("wake_lock");
        pre_light.setOnPreferenceClickListener(this);

        txt_title = (TextView) findViewById(R.id.txt_header_title2);
        txt_title.setText(getResources().getString(R.string.setting));
        getListView().setDivider(getResources().getDrawable(R.drawable.divider_horizontal_line));

        listPreference = (ListPreference) findPreference("text_size");
        sPreference = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("text_size")) {
            listPreference.setSummary(getString(R.string.current_text_size) + getTextSize());
        } else if (key.equals("light")) {

        }

    }

    private String getTextSize() {

        if (sPreference.getString("text_size", "key2").equals("key0")) {
            return getString(R.string.larger);
        } else if (sPreference.getString("text_size", "key2").equals("key1")) {
            return getString(R.string.largest);
        } else if (sPreference.getString("text_size", "key2").equals("key3")) {
            return getString(R.string.smaller);
        } else if (sPreference.getString("text_size", "key2").equals("key4")) {
            return getString(R.string.smallest);
        } else {
            return getString(R.string.nomal);
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        if (preference.getKey().equals("wake_lock")) {

            if (sPreference.getBoolean("wake_lock", true)) {
                wh_app.releaseWakeLock();
            } else {
                wh_app.acquireWakeLock();
            }

        }
        return false;
    }
}
