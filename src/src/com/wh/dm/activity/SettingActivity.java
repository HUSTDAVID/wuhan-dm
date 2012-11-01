
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class SettingActivity extends PreferenceActivity implements OnPreferenceClickListener,
        OnSharedPreferenceChangeListener {

    TextView txt_title;
    Preference pref_login;
    Preference pref_account;
    Preference pref_flow;
    Preference pref_cache;
    Preference pref_more;
    Preference pref_feedback;
    Preference pref_about;

    SharedPreferences sPreference;

    private ImageButton btnBack;

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preference);
        setContentView(R.layout.activity_setting);
        init();
    }

    public void onResume() {

        super.onResume();
        setFlowSummary(sPreference, pref_flow);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        MobclickAgent.onResume(this);
    }

    public void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void init() {

        sPreference = PreferenceManager.getDefaultSharedPreferences(this);

        txt_title = (TextView) findViewById(R.id.txt_header_title2);
        txt_title.setText(getResources().getString(R.string.setting));
        btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });
        getListView().setDivider(getResources().getDrawable(R.drawable.divider_horizontal_line));
        pref_login = findPreference("login");
        pref_account = findPreference("account");
        pref_flow = findPreference("flow");
        pref_cache = findPreference("cache");
        pref_more = findPreference("more");
        pref_feedback = findPreference("feedback");
        pref_about = findPreference("about");

        pref_login.setOnPreferenceClickListener(this);
        pref_account.setOnPreferenceClickListener(this);
        pref_cache.setOnPreferenceClickListener(this);
        pref_more.setOnPreferenceClickListener(this);
        pref_feedback.setOnPreferenceClickListener(this);
        pref_about.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        if (preference.getKey().equals("login")) {
            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (preference.getKey().equals("account")) {
            Intent intent = new Intent(SettingActivity.this, WeiboActivity.class);
            startActivity(intent);
        } else if (preference.getKey().equals("cache")) {

        } else if (preference.getKey().equals("more")) {
            Intent intent = new Intent(SettingActivity.this, MoreActivity.class);
            startActivity(intent);
        } else if (preference.getKey().equals("feedback")) {
            Intent intent = new Intent(SettingActivity.this, FeedbackActivity.class);
            startActivity(intent);
        } else if (preference.getKey().equals("about")) {
            Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("flow")) {
            setFlowSummary(sharedPreferences, pref_flow);
        }

    }

    // set flow control preference
    private void setFlowSummary(SharedPreferences sharedPreferences, Preference pref) {

        if (sharedPreferences.getString("flow", "key0").equals("key0")) {
            pref.setSummary(getResources().getStringArray(R.array.flow_control)[0]);
        } else if (sharedPreferences.getString("flow", "key0").equals("key1")) {
            pref.setSummary(getResources().getStringArray(R.array.flow_control)[1]);
        } else if (sharedPreferences.getString("flow", "key0").equals("key2")) {
            pref.setSummary(getResources().getStringArray(R.array.flow_control)[2]);
        }
    }

}
