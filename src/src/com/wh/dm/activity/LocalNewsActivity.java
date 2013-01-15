
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.preference.Preferences;
import com.wh.dm.type.NewsType;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

public class LocalNewsActivity extends ActivityGroup implements OnClickListener {

    private TextView txtTitle;
    private TextView txtSelectedItem;

    private RelativeLayout relMain;
    private LayoutParams params = null;

    private LinearLayout menuLinerLayout;
    private ArrayList<TextView> menuList;

    private TextView txtHeadline;
    private TextView txtHouse;
    private TextView txtCar;
    private TextView txtFashion;
    private TextView txtLift;
    private TextView txtTravel;

    private ImageButton btnMessage;
    private TextView txtNum;
    private View vMain;
    private int itemWidth = 0;
    Intent intent = null;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private final int MSG_GET_NEWS_TYPE = 0;
    private GetNewsTypeTask getNewsTypeTask = null;
    private ArrayList<NewsType> newsSort = null;
    private String[] newsSortNames;
    private String[] newsIds;

    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MSG_GET_NEWS_TYPE) {
                if (getNewsTypeTask != null) {
                    getNewsTypeTask.cancel(true);
                    getNewsTypeTask = null;
                }
                getNewsTypeTask = new GetNewsTypeTask();
                getNewsTypeTask.execute();
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_news);
        initViews();
        showMessage();

    }

    @Override
    public void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initViews() {

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();

        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getResources().getString(R.string.news));

        // add list top
        menuList = new ArrayList<TextView>();
        menuLinerLayout = (LinearLayout) findViewById(R.id.linearLayoutMenu);
        menuLinerLayout.setOrientation(LinearLayout.HORIZONTAL);
        // ²ÎÊýÉèÖÃ
        LinearLayout.LayoutParams menuLinerLayoutParames = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        menuLinerLayoutParames.gravity = Gravity.CENTER_HORIZONTAL;

        txtHeadline = new TextView(this);
        txtHouse = new TextView(this);
        txtCar = new TextView(this);
        txtFashion = new TextView(this);
        txtLift = new TextView(this);
        txtTravel = new TextView(this);

        SharedPreferences preference = PreferenceManager
                .getDefaultSharedPreferences(LocalNewsActivity.this);
        String newsAllSort = preference.getString(Preferences.NEWS_ALL_SORT,
                getString(R.string.news_all_sort));
        String newsAllId = preference.getString(Preferences.NEWS_ALL_SORT,
                getString(R.string.news_all_sort));
        newsSortNames = newsAllSort.split("\\:");
        newsIds = newsAllId.split("\\:");

        txtHeadline = initMenu(txtHeadline, getResources().getString(R.string.headline));
        txtHouse = initMenu(txtHouse, newsSortNames[0]);
        txtCar = initMenu(txtCar, newsSortNames[1]);
        txtFashion = initMenu(txtFashion, newsSortNames[2]);
        txtLift = initMenu(txtLift, newsSortNames[3]);
        txtTravel = initMenu(txtTravel, newsSortNames[4]);

        txtHeadline.setId(1);
        txtHouse.setId(2);
        txtCar.setId(3);
        txtFashion.setId(4);
        txtLift.setId(5);
        txtTravel.setId(6);

        menuList.add(txtHeadline);
        menuList.add(txtHouse);
        menuList.add(txtCar);
        menuList.add(txtFashion);
        menuList.add(txtLift);
        menuList.add(txtTravel);

        menuLinerLayout.addView(txtHeadline);
        menuLinerLayout.addView(txtHouse);
        menuLinerLayout.addView(txtCar);
        menuLinerLayout.addView(txtFashion);
        menuLinerLayout.addView(txtLift);
        menuLinerLayout.addView(txtTravel);

        if (newsSortNames.length > 5) {
            for (int i = 5; i < newsSortNames.length; i++) {
                TextView txtOther = new TextView(LocalNewsActivity.this);
                txtOther = initMenu(txtOther, newsSortNames[i]);
                txtOther.setId(i + 2);
                txtOther.setOnClickListener(new NewsItemOnClickListener());
                menuLinerLayout.addView(txtOther);
                menuList.add(txtOther);

            }
        }

        handler.sendEmptyMessage(MSG_GET_NEWS_TYPE);

        itemWidth = getScreenWidth() / 6;

        txtHeadline.setOnClickListener(new NewsItemOnClickListener());
        txtHouse.setOnClickListener(new NewsItemOnClickListener());
        txtCar.setOnClickListener(new NewsItemOnClickListener());
        txtFashion.setOnClickListener(new NewsItemOnClickListener());
        txtLift.setOnClickListener(new NewsItemOnClickListener());
        txtTravel.setOnClickListener(new NewsItemOnClickListener());

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(itemWidth,
                LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        // set the activity of headline
        intent = new Intent(LocalNewsActivity.this, HeadNewsActivity.class);
        relMain = (RelativeLayout) findViewById(R.id.rel_news_main);
        vMain = getLocalActivityManager().startActivity("Headline", intent).getDecorView();
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        relMain.addView(vMain, params);
        setCurTxt(1);
    }

    private class NewsItemOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case 1:
                    setCurTxt(1);
                    intent.setClass(LocalNewsActivity.this, HeadNewsActivity.class);
                    vMain = getLocalActivityManager().startActivity("Headline", intent)
                            .getDecorView();

                    break;

                case 2:
                    setCurTxt(2);
                    intent.setClass(LocalNewsActivity.this, HouseNewsActivity.class);
                    intent.putExtra("id", newsIds[0]);
                    vMain = getLocalActivityManager().startActivity("House", intent).getDecorView();
                    break;

                case 3:
                    setCurTxt(3);
                    intent.setClass(LocalNewsActivity.this, CarNewsActivity.class);
                    intent.putExtra("id", newsIds[1]);
                    vMain = getLocalActivityManager().startActivity("Car", intent).getDecorView();
                    break;

                case 4:
                    setCurTxt(4);
                    intent.setClass(LocalNewsActivity.this, FashionNewsActivity.class);
                    intent.putExtra("id", newsIds[2]);
                    vMain = getLocalActivityManager().startActivity("Fashion", intent)
                            .getDecorView();
                    break;

                case 5:
                    setCurTxt(5);
                    intent.setClass(LocalNewsActivity.this, LifeNewsActivity.class);
                    intent.putExtra("id", newsIds[3]);
                    vMain = getLocalActivityManager().startActivity("Life", intent).getDecorView();
                    break;

                case 6:
                    setCurTxt(6);
                    intent.setClass(LocalNewsActivity.this, TravelNewsActivity.class);
                    intent.putExtra("id", newsIds[4]);
                    vMain = getLocalActivityManager().startActivity("Travel", intent)
                            .getDecorView();
                    break;
                default:
                    int id = v.getId();
                    setCurTxt(id);
                    getLocalActivityManager().destroyActivity("other", true);
                    intent.setClass(LocalNewsActivity.this, NewsOtherSortActivity.class);
                    intent.putExtra("id", newsIds[id - 2]);
                    vMain = getLocalActivityManager().startActivity("other", intent).getDecorView();
                    break;
            }

            // set main content
            relMain.removeAllViews();
            relMain.addView(vMain, params);
        }

    }

    private int getScreenWidth() {

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        return screenWidth;
    }

    private void setCurTxt(int i) {

        int pos = i - 1;
        int size = menuList.size();
        for (int j = 0; j < size; j++) {
            if (j == pos) {
                menuList.get(j).setSelected(true);
                menuList.get(j).setTextColor(Color.WHITE);
            } else {
                menuList.get(j).setSelected(false);
                menuList.get(j).setTextColor(Color.BLACK);
            }
        }

    }

    public void showMessage() {

        btnMessage = (ImageButton) findViewById(R.id.btn_message);
        txtNum = (TextView) findViewById(R.id.txt_message_num);
        btnMessage.setOnClickListener(this);
        txtNum.setOnClickListener(this);
        int num = Preferences.getMsgNum(LocalNewsActivity.this);

        txtNum.setText("" + num);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_message:
            case R.id.txt_message_num:
                Intent intent = new Intent(LocalNewsActivity.this, MessageActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    class GetNewsTypeTask extends AsyncTask<Void, Void, ArrayList<NewsType>> {

        Exception reason;

        @Override
        protected ArrayList<NewsType> doInBackground(Void... params) {

            try {
                newsSort = wh_dmApi.getNewsType();
                return newsSort;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsType> result) {

            if (result != null && result.size() > 0) {

                String newsAllSort = "";
                String newsAllId = "";
                int size = result.size();
                int length = newsSortNames.length;
                // index 0 is headline
                for (int i = 0; i < length; i++) {
                    // index 0 is headline,so get result i to menulist i+1
                    menuList.get(i + 1).setText(result.get(i).getTypename());
                }
                for (int i = 0; i < size; i++) {
                    newsAllSort += result.get(i).getTypename();
                    newsAllId += result.get(i).getId();
                    if (i != size) {
                        newsAllSort += ":";
                        newsAllId += ":";
                    }

                }
                newsIds = newsAllId.split("\\:");
                if (size > length) {

                    for (int i = length; i < size; i++) {
                        TextView txtOther = new TextView(LocalNewsActivity.this);
                        txtOther = initMenu(txtOther, result.get(i).getTypename());
                        txtOther.setId(i + 2);
                        txtOther.setOnClickListener(new NewsItemOnClickListener());
                        menuLinerLayout.addView(txtOther);
                        menuList.add(txtOther);

                    }
                }

                Preferences.saveNewsType(LocalNewsActivity.this, newsAllSort, newsAllId);
            }
            super.onPostExecute(result);
        }
    }

    // init menu text view
    private TextView initMenu(TextView txtMenu, String txt) {

        txtMenu.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.FILL_PARENT));
        txtMenu.setPadding(15, 10, 10, 10);
        txtMenu.setText(txt);
        txtMenu.setTextColor(Color.BLACK);
        txtMenu.setTextSize(18);
        txtMenu.setGravity(Gravity.CENTER_HORIZONTAL);
        txtMenu.setBackgroundResource(R.drawable.list_top);
        return txtMenu;
    }
}
