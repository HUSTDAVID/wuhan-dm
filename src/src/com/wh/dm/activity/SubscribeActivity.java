
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.preference.Preferences;
import com.wh.dm.type.MagazineSort;
import com.wh.dm.util.NotificationUtil;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

public class SubscribeActivity extends ActivityGroup {

    LinearLayout linearListTop;
    LinearLayout linearSubSearch;
    // topbar
    private TextView txtTitle;
    private TextView txtSelectedItem;

    private RelativeLayout relMain;
    private LayoutParams params = null;

    private TextView txtHot;
    private TextView txtCar;
    private TextView txtGirl;
    private TextView txtPhotograph;
    private TextView txtFun;
    private ImageView imgSearch;
    private ImageButton btnBack;
    private View vMain;

    private EditText edtKey;
    private Button btnCSearch;

    private final int startX = 0;
    private int itemWidth = 0;
    Intent intent = null;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private final int MSG_GET_MAGAZIEN_SORT = 0;
    private ArrayList<MagazineSort> sortList;
    private GetMagazineSortTask getMagazineSortTask = null;
    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MSG_GET_MAGAZIEN_SORT) {
                if (getMagazineSortTask != null) {
                    getMagazineSortTask.cancel(true);
                    getMagazineSortTask = null;
                }
                getMagazineSortTask = new GetMagazineSortTask();
                getMagazineSortTask.execute();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_subscribe);

        initViews();
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

        linearListTop = (LinearLayout) findViewById(R.id.linear_sub_list_top);
        linearSubSearch = (LinearLayout) findViewById(R.id.linear_sub_search);

        // init header

        TextView txtTitle = (TextView) findViewById(R.id.txt_header3_title);
        TextView txtManager = (TextView) findViewById(R.id.txt_total_reply);
        txtTitle.setText(getResources().getString(R.string.subscribe));
        txtManager.setText(getResources().getString(R.string.sub_manager));

        edtKey = (EditText) findViewById(R.id.edt_sub_search);
        btnCSearch = (Button) findViewById(R.id.btn_sub_csearch);

        btnCSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String key = edtKey.getText().toString();
                if (key.equals("") || key == null) {
                    NotificationUtil.showShortToast("«Î ‰»Îπÿº¸¥ ", SubscribeActivity.this);
                } else {
                    getLocalActivityManager().destroyActivity("search", true);
                    intent.setClass(SubscribeActivity.this, SearchMagazineActivity.class);
                    intent.putExtra("key", key);
                    Window window = getLocalActivityManager().startActivity("search", intent);
                    vMain = window.getDecorView();
                    relMain.removeAllViews();
                    relMain.addView(vMain, params);
                }

            }
        });

        txtManager.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SubscribeActivity.this, SubManagerActivity.class);
                startActivity(intent);

            }
        });

        //

        // txtTitle = (TextView) findViewById(R.id.txt_title);
        // txtTitle.setText(getResources().getString(R.string.news));

        SharedPreferences preference = PreferenceManager
                .getDefaultSharedPreferences(SubscribeActivity.this);
        String one = preference.getString(Preferences.MAGAZINE_ONE,
                getResources().getString(R.string.hot));
        String two = preference.getString(Preferences.MAGAZINE_TWO,
                getResources().getString(R.string.car));
        String three = preference.getString(Preferences.MAGAZINE_THREE,
                getResources().getString(R.string.girl));
        String four = preference.getString(Preferences.MAGAZINE_FOUR,
                getResources().getString(R.string.photograph));

        txtHot = (TextView) findViewById(R.id.txt_sub_hot);
        txtCar = (TextView) findViewById(R.id.txt_sub_car);
        txtGirl = (TextView) findViewById(R.id.txt_sub_girl);
        txtPhotograph = (TextView) findViewById(R.id.txt_sub_photograph);
        txtFun = (TextView) findViewById(R.id.txt_sub_fun);

        txtCar.setText(one);
        txtGirl.setText(two);
        txtPhotograph.setText(three);
        txtFun.setText(four);
        imgSearch = (ImageView) findViewById(R.id.img_sub_search);
        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

        itemWidth = txtHot.getWidth();

        txtHot.setOnClickListener(new NewsItemOnClickListener());
        txtCar.setOnClickListener(new NewsItemOnClickListener());
        txtGirl.setOnClickListener(new NewsItemOnClickListener());
        txtPhotograph.setOnClickListener(new NewsItemOnClickListener());
        txtFun.setOnClickListener(new NewsItemOnClickListener());
        imgSearch.setOnClickListener(new NewsItemOnClickListener());

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(itemWidth,
                LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        param.leftMargin = startX;

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        handler.sendEmptyMessage(MSG_GET_MAGAZIEN_SORT);

        // set the activity of hot
        intent = new Intent(SubscribeActivity.this, Sub_HotActivity.class);
        relMain = (RelativeLayout) findViewById(R.id.rel_sub_main);
        vMain = getLocalActivityManager().startActivity("Hot", intent).getDecorView();
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        relMain.addView(vMain, params);
        setCurTxt(1);

    }

    private class NewsItemOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            itemWidth = txtHot.getWidth();
            switch (v.getId()) {
                case R.id.txt_sub_hot:
                    setCurTxt(1);
                    intent.setClass(SubscribeActivity.this, Sub_HotActivity.class);
                    vMain = getLocalActivityManager().startActivity("hot", intent).getDecorView();
                    break;

                case R.id.txt_sub_car:
                    setCurTxt(2);
                    intent.setClass(SubscribeActivity.this, Sub_CarActivity.class);
                    vMain = getLocalActivityManager().startActivity("car", intent).getDecorView();
                    break;

                case R.id.txt_sub_girl:
                    setCurTxt(3);
                    intent.setClass(SubscribeActivity.this, Sub_GirlActivity.class);
                    vMain = getLocalActivityManager().startActivity("girl", intent).getDecorView();
                    break;

                case R.id.txt_sub_photograph:
                    setCurTxt(4);
                    intent.setClass(SubscribeActivity.this, Sub_ShootActivity.class);
                    vMain = getLocalActivityManager().startActivity("shoot", intent).getDecorView();
                    break;

                case R.id.txt_sub_fun:
                    setCurTxt(5);
                    intent.setClass(SubscribeActivity.this, Sub_FunActivity.class);
                    vMain = getLocalActivityManager().startActivity("fun", intent).getDecorView();
                    break;

                case R.id.img_sub_search:
                    linearListTop.setVisibility(View.GONE);
                    linearSubSearch.setVisibility(View.VISIBLE);
                    break;
            }

            // set main content
            relMain.removeAllViews();
            relMain.addView(vMain, params);
        }

    }

    private void setCurTxt(int i) {

        switch (i) {
            case 1:
                txtHot.setSelected(true);
                txtHot.setTextColor(Color.WHITE);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtGirl.setSelected(false);
                txtGirl.setTextColor(Color.BLACK);
                txtPhotograph.setSelected(false);
                txtPhotograph.setTextColor(Color.BLACK);
                txtFun.setSelected(false);
                txtFun.setTextColor(Color.BLACK);
                break;
            case 2:
                txtHot.setSelected(false);
                txtHot.setTextColor(Color.BLACK);
                txtCar.setSelected(true);
                txtCar.setTextColor(Color.WHITE);
                txtGirl.setSelected(false);
                txtGirl.setTextColor(Color.BLACK);
                txtPhotograph.setSelected(false);
                txtPhotograph.setTextColor(Color.BLACK);
                txtFun.setSelected(false);
                txtFun.setTextColor(Color.BLACK);
                break;
            case 3:
                txtHot.setSelected(false);
                txtHot.setTextColor(Color.BLACK);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtGirl.setSelected(true);
                txtGirl.setTextColor(Color.WHITE);
                txtPhotograph.setSelected(false);
                txtPhotograph.setTextColor(Color.BLACK);
                txtFun.setSelected(false);
                txtFun.setTextColor(Color.BLACK);
                break;
            case 4:
                txtHot.setSelected(false);
                txtHot.setTextColor(Color.BLACK);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtGirl.setSelected(false);
                txtGirl.setTextColor(Color.BLACK);
                txtPhotograph.setSelected(true);
                txtPhotograph.setTextColor(Color.WHITE);
                txtFun.setSelected(false);
                txtFun.setTextColor(Color.BLACK);
                break;
            case 5:
                txtHot.setSelected(false);
                txtHot.setTextColor(Color.BLACK);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtGirl.setSelected(false);
                txtGirl.setTextColor(Color.BLACK);
                txtPhotograph.setSelected(false);
                txtPhotograph.setTextColor(Color.BLACK);
                txtFun.setSelected(true);
                txtFun.setTextColor(Color.WHITE);
                break;
        }
    }

    class GetMagazineSortTask extends AsyncTask<Void, Void, ArrayList<MagazineSort>> {

        Exception reason = null;

        @Override
        protected ArrayList<MagazineSort> doInBackground(Void... params) {

            try {
                sortList = wh_dmApi.getMagazineSort();
                return sortList;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MagazineSort> result) {

            if (sortList != null && sortList.size() > 0) {

                String one = sortList.get(0).getTypename();
                String two = sortList.get(1).getTypename();
                String three = sortList.get(2).getTypename();
                String four = sortList.get(3).getTypename();
                int idOne = sortList.get(0).getId();
                int idTwo = sortList.get(1).getId();
                int idThree = sortList.get(2).getId();
                int idFour = sortList.get(3).getId();
                txtCar.setText(one);
                txtGirl.setText(two);
                txtPhotograph.setText(three);
                txtFun.setText(four);

                Preferences.saveMagazienType(SubscribeActivity.this, one, two, three, four, idOne,
                        idTwo, idThree, idFour);

            }
            super.onPostExecute(result);
        }
    }
}
