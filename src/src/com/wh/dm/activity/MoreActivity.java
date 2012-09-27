
package com.wh.dm.activity;

import com.wh.dm.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.TextView;

public class MoreActivity extends PreferenceActivity {

    TextView txt_title;

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preference_more);
        setContentView(R.layout.activity_setting);
        init();
    }

    public void init() {

        txt_title = (TextView) findViewById(R.id.txt_header_title2);
        txt_title.setText(getResources().getString(R.string.setting));
        getListView().setDivider(getResources().getDrawable(R.drawable.divider_horizontal_line));
    }

}
