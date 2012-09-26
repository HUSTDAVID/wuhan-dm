
package com.wh.dm.activity;

import com.wh.dm.R;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.TextView;

public class SettingActivity extends PreferenceActivity implements OnPreferenceClickListener {

    TextView txt_title;
    Preference pref_login;
    Preference pref_account;
    Preference pref_cache;
    Preference pref_more;
    Preference pref_feedback;
    Preference pref_about;

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preference);
        setContentView(R.layout.activity_setting);
        init();
    }

    public void init() {

        txt_title = (TextView) findViewById(R.id.txt_header_title2);
        txt_title.setText(getResources().getString(R.string.setting));
        getListView().setDivider(getResources().getDrawable(R.drawable.divider_horizontal_line));
        pref_login = findPreference("login");
        pref_account = findPreference("account");
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
}
