
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
        // ≤Œ ˝…Ë÷√
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
        String one = preference.getString(Preferences.NEWS_ONE,
                getResources().getString(R.string.house));
        String two = preference.getString(Preferences.NEWS_TWO,
                getResources().getString(R.string.car));
        String three = preference.getString(Preferences.NEWS_THREE,
                getResources().getString(R.string.digital));
        String four = preference.getString(Preferences.NEWS_FOUR,
                getResources().getString(R.string.lift));
        String five = preference.getString(Preferences.NEWS_FIVE,
                getResources().getString(R.string.traval));

        txtHeadline = initMenu(txtHeadline, getResources().getString(R.string.headline));
        txtHouse = initMenu(txtHouse, one);
        txtCar = initMenu(txtCar, two);
        txtFashion = initMenu(txtFashion, three);
        txtLift = initMenu(txtLift, four);
        txtTravel = initMenu(txtTravel, five);

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
                    vMain = getLocalActivityManager().startActivity("House", intent).getDecorView();
                    break;

                case 3:
                    setCurTxt(3);
                    intent.setClass(LocalNewsActivity.this, CarNewsActivity.class);
                    vMain = getLocalActivityManager().startActivity("Car", intent).getDecorView();
                    break;

                case 4:
                    setCurTxt(4);
                    intent.setClass(LocalNewsActivity.this, FashionNewsActivity.class);
                    vMain = getLocalActivityManager().startActivity("Fashion", intent)
                            .getDecorView();
                    break;

                case 5:
                    setCurTxt(5);
                    intent.setClass(LocalNewsActivity.this, LifeNewsActivity.class);
                    vMain = getLocalActivityManager().startActivity("Life", intent).getDecorView();
                    break;

                case 6:
                    setCurTxt(6);
                    intent.setClass(LocalNewsActivity.this, TravelNewsActivity.class);
                    vMain = getLocalActivityManager().startActivity("Travel", intent)
                            .getDecorView();
                    break;
                default:
                    int id = v.getId();
                    setCurTxt(id);
                    getLocalActivityManager().destroyActivity("other", true);
                    intent.setClass(LocalNewsActivity.this, NewsOtherSortActivity.class);
                    intent.putExtra("id", newsSort.get(id - 2).getId());
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
                String one = newsSort.get(0).getTypename();
                String two = newsSort.get(1).getTypename();
                String three = newsSort.get(2).getTypename();
                String four = newsSort.get(3).getTypename();
                String five = newsSort.get(4).getTypename();
                int idOne = newsSort.get(0).getId();
                int idTwo = newsSort.get(1).getId();
                int idThree = newsSort.get(2).getId();
                int idFour = newsSort.get(3).getId();
                int idFive = newsSort.get(4).getId();

                // refresh news sort
                txtHouse.setText(one);
                txtCar.setText(two);
                txtFashion.setText(three);
                txtLift.setText(four);
                txtTravel.setText(five);

                if (result.size() > 5) {
                    for (int i = 5; i < result.size(); i++) {
                        TextView txtOther = new TextView(LocalNewsActivity.this);
                        txtOther = initMenu(txtOther, result.get(i).getTypename());
                        txtOther.setId(i + 2);
                        txtOther.setOnClickListener(new NewsItemOnClickListener());
                        menuLinerLayout.addView(txtOther);
                        menuList.add(txtOther);

                    }
                }

                Preferences.saveNewsType(LocalNewsActivity.this, one, two, three, four, five,
                        idOne, idTwo, idThree, idFour, idFive);
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
