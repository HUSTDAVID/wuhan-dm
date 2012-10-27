
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class MoreActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    TextView txt_title;
    ListPreference listPreference;
    SharedPreferences sPreference;

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
}
