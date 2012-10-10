
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class DM_Tab_2Activity extends TabActivity implements OnTabChangeListener {

    private static final int MAGAZINE = 0;
    private static final int INTERACTION = 1;
    private static final int SUBSCRIBE = 2;
    private static final int STORE = 3;

    TabHost tabHost;
    private Button btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tab);

        initTabs();
    }

    private void initTabs() {

        tabHost = getTabHost();
        Intent intent = getIntent();
        Bundle flag = intent.getExtras();
        if (flag.getInt("dm") == MainActivity.DM_FULL) {
            tabHost.addTab(tabHost.newTabSpec("magazine").setIndicator(createTabView(MAGAZINE))
                    .setContent(new Intent(this, DM_MzineFullActivity.class)));

        } else if (flag.getInt("dm") == MainActivity.DM_PICS_TXT) {
            tabHost.addTab(tabHost.newTabSpec("magazine").setIndicator(createTabView(MAGAZINE))
                    .setContent(new Intent(this, DM_MZine1Activity.class)));
        } else if (flag.getInt("dm") == MainActivity.DM_PICS_TXT2) {
            tabHost.addTab(tabHost.newTabSpec("magazine").setIndicator(createTabView(MAGAZINE))
                    .setContent(new Intent(this, DM_TempActivity.class)));
        } else if (flag.getInt("dm") == MainActivity.DM_PICS_TXT3) {
            tabHost.addTab(tabHost.newTabSpec("magazine").setIndicator(createTabView(MAGAZINE))
                    .setContent(new Intent(this, DM_TempActivity.class)));
        }

        tabHost.addTab(tabHost.newTabSpec("interaction").setIndicator(createTabView(INTERACTION))
                .setContent(new Intent(this, DM_InteractionActivity.class)));

        tabHost.addTab(tabHost.newTabSpec("subscribe").setIndicator(createTabView(SUBSCRIBE))
                .setContent(new Intent(this, SubscribeActivity.class)));

        tabHost.addTab(tabHost.newTabSpec("store").setIndicator(createTabView(STORE))
                .setContent(new Intent(this, CollectActivity.class)));

        tabHost.setOnTabChangedListener(this);
        changeTabTxtColor();
    }

    private View createTabView(int tab) {

        View view = LayoutInflater.from(this).inflate(R.layout.tab_widget, null);
        TextView tv = (TextView) view.findViewById(R.id.txt_tab_widget);
        ImageView iv = (ImageView) view.findViewById(R.id.img_tab_widget);
        switch (tab) {
            case MAGAZINE:
                iv.setImageResource(R.drawable.tab_magazine);
                tv.setText(getResources().getString(R.string.magazine));
                break;
            case INTERACTION:
                iv.setImageResource(R.drawable.tab_interaction);
                tv.setText(getResources().getString(R.string.interaction));
                break;
            case SUBSCRIBE:
                iv.setImageResource(R.drawable.tab_subscribe);
                tv.setText(getResources().getString(R.string.subscribe));
                break;
            case STORE:
                iv.setImageResource(R.drawable.tab_store);
                tv.setText(getResources().getString(R.string.store));
                break;
        }

        return view;
    }

    @Override
    public void onTabChanged(String tabId) {

        changeTabTxtColor();
    }

    private void changeTabTxtColor() {

        for (int i = 0; i < 4; i++) {
            View view = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) view.findViewById(R.id.txt_tab_widget);
            if (i == tabHost.getCurrentTab()) {
                tv.setTextColor(getResources().getColor(R.color.white));
            } else {
                tv.setTextColor(getResources().getColor(R.color.tab_txt_normal_color));
            }
        }
    }

}
