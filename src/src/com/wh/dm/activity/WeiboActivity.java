
package com.wh.dm.activity;

import com.umeng.api.sns.UMSnsService;
import com.wh.dm.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

public class WeiboActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    private CheckBoxPreference pref_sina;
    private CheckBoxPreference pref_tenc;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        addPreferencesFromResource(R.xml.preference_account);
        setContentView(R.layout.activity_toweibo);
        UMSnsService.UseLocation = true;
        UMSnsService.LocationAuto = true;
        UMSnsService.LOCATION_VALID_TIME = 180000;
        initViews();

    }

    private void initViews() {

        TextView txtHeader = (TextView) findViewById(R.id.txt_header_title2);
        txtHeader.setText(getResources().getString(R.string.toweibo));
        pref_sina = (CheckBoxPreference) findPreference("sinaweibo");
        pref_tenc = (CheckBoxPreference) findPreference("tencweibo");
        btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("sina")) {
            boolean isChecked = sharedPreferences.getBoolean("sina", false);
            if (isChecked) {
                UMSnsService.oauthSina(WeiboActivity.this, null);
                ((CheckBox) findViewById(pref_sina.getWidgetLayoutResource())).setText("xx");
            } else {
                UMSnsService.getAccessToken(this, UMSnsService.SHARE_TO.SINA);
            }
        } else if (key.equals("tenc")) {
            boolean isChecked = sharedPreferences.getBoolean("tenc", false);
            if (isChecked) {
                UMSnsService.oauthTenc(WeiboActivity.this, null);
                ((CheckBox) findViewById(pref_tenc.getWidgetLayoutResource())).setText("xx");
            } else {
                UMSnsService.getAccessToken(this, UMSnsService.SHARE_TO.TENC);
            }
        }

    }
}
