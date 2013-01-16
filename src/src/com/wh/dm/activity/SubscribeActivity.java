
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
import android.view.Gravity;
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

    private LinearLayout menuLinerLayout;
    private ArrayList<TextView> menuList;
    private String[] magazineSortNames;
    private String[] magazineIds;

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
                    NotificationUtil.showShortToast("请输入关键词", SubscribeActivity.this);
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

        menuList = new ArrayList<TextView>();
        menuLinerLayout = (LinearLayout) findViewById(R.id.linearLayoutMenu);
        menuLinerLayout.setOrientation(LinearLayout.HORIZONTAL);
        // 参数设置
        LinearLayout.LayoutParams menuLinerLayoutParames = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        menuLinerLayoutParames.gravity = Gravity.CENTER_HORIZONTAL;

        SharedPreferences preference = PreferenceManager
                .getDefaultSharedPreferences(SubscribeActivity.this);
        String magazineAllSortNameStr = preference.getString(Preferences.MAGAZINE_ALL_SORT,
                getString(R.string.magazine_all_sort));
        String magazineAllIdsStr = preference.getString(Preferences.MAGAZINE_ALL_SORT,
                getString(R.string.magazine_all_id));

        magazineSortNames = magazineAllSortNameStr.split("\\:");
        magazineIds = magazineAllIdsStr.split("\\:");

        TextView txtHot = new TextView(this);
        txtHot = initMenu(txtHot, getResources().getString(R.string.hot));
        txtHot.setId(1);
        txtHot.setOnClickListener(new NewsItemOnClickListener());
        menuList.add(txtHot);
        menuLinerLayout.addView(txtHot);

        for (int i = 0; i < magazineSortNames.length; i++) {
            TextView txtView = new TextView(this);
            txtView = initMenu(txtView, magazineSortNames[i]);
            txtView.setId(i + 2);
            txtView.setOnClickListener(new NewsItemOnClickListener());
            menuList.add(txtView);
            menuLinerLayout.addView(txtView);
        }

        imgSearch = (ImageView) findViewById(R.id.img_sub_search);
        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

        imgSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                linearListTop.setVisibility(View.GONE);
                linearSubSearch.setVisibility(View.VISIBLE);

            }
        });

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

            switch (v.getId()) {
                case 1:
                    setCurTxt(1);
                    intent.setClass(SubscribeActivity.this, Sub_HotActivity.class);
                    vMain = getLocalActivityManager().startActivity("hot", intent).getDecorView();
                    break;

                case 2:
                    setCurTxt(2);
                    intent.setClass(SubscribeActivity.this, Sub_CarActivity.class);
                    intent.putExtra("id", magazineIds[0]);
                    vMain = getLocalActivityManager().startActivity("car", intent).getDecorView();
                    break;

                case 3:
                    setCurTxt(3);
                    intent.setClass(SubscribeActivity.this, Sub_GirlActivity.class);
                    intent.putExtra("id", magazineIds[1]);
                    vMain = getLocalActivityManager().startActivity("girl", intent).getDecorView();
                    break;

                case 4:
                    setCurTxt(4);
                    intent.setClass(SubscribeActivity.this, Sub_ShootActivity.class);
                    intent.putExtra("id", magazineIds[2]);
                    vMain = getLocalActivityManager().startActivity("shoot", intent).getDecorView();
                    break;

                case 5:
                    setCurTxt(5);
                    intent.setClass(SubscribeActivity.this, Sub_FunActivity.class);
                    intent.putExtra("id", magazineIds[3]);
                    vMain = getLocalActivityManager().startActivity("fun", intent).getDecorView();
                    break;
                default:
                    int id = v.getId();
                    setCurTxt(id);
                    getLocalActivityManager().destroyActivity("other", true);
                    intent.setClass(SubscribeActivity.this, SubOtherSortActivity.class);
                    intent.putExtra("id", magazineIds[id - 2]);
                    vMain = getLocalActivityManager().startActivity("other", intent).getDecorView();
                    break;
            }

            // set main content
            relMain.removeAllViews();
            relMain.addView(vMain, params);
        }

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

                String magazineAllSortStr = "";
                String magazineAllId = "";
                int size = result.size();
                int length = magazineSortNames.length;
                // index 0 is headline
                for (int i = 0; i < length; i++) {
                    menuList.get(i + 1).setText(result.get(i).getTypename());
                }
                for (int i = 0; i < size; i++) {
                    magazineAllSortStr += result.get(i).getTypename();
                    magazineAllId += result.get(i).getId();
                    magazineAllSortStr += ":";
                    magazineAllId += ":";
                }
                magazineIds = magazineAllId.split("\\:");
                if (size > length) {

                    for (int i = length; i < size; i++) {
                        TextView txtOther = new TextView(SubscribeActivity.this);
                        txtOther = initMenu(txtOther, result.get(i).getTypename());
                        txtOther.setId(i + 2);
                        txtOther.setOnClickListener(new NewsItemOnClickListener());
                        menuLinerLayout.addView(txtOther);
                        menuList.add(txtOther);

                    }
                }

                Preferences.saveMagazienType(SubscribeActivity.this, magazineAllSortStr,
                        magazineAllId);

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
