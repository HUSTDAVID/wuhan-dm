
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.preference.Preferences;
import com.wh.dm.type.PhotoSort;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotosActivity extends ActivityGroup implements OnClickListener {

    private RelativeLayout relMain;
    private LayoutParams params = null;

    private TextView txtAll;
    private TextView txtHot;
    private TextView txtCar;
    private TextView txtGirl;
    private TextView txtPhotograph;
    private TextView txtFun;
    private ImageButton btnMessage;
    private TextView txtNum;
    private View vMain;
    private int itemWidth = 0;
    Intent intent = null;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_DMApi;
    private GetPhotoSortTask getPhotoSortTask = null;
    private final int MSG_GET_PHOTO_SORT = 0;
    private ArrayList<PhotoSort> sortList = null;

    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MSG_GET_PHOTO_SORT) {
                if (getPhotoSortTask != null) {
                    getPhotoSortTask.cancel(true);
                    getPhotoSortTask = null;
                }
                getPhotoSortTask = new GetPhotoSortTask();
                getPhotoSortTask.execute();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photos);

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
        wh_DMApi = wh_dmApp.getWH_DMApi();

        // init header
        View header = LayoutInflater.from(this).inflate(R.layout.header_title3, null);
        header.setBackgroundResource(R.drawable.topbar_black_bg);
        RelativeLayout rel = (RelativeLayout) header.findViewById(R.id.rel_header3);
        rel.setBackgroundResource(R.drawable.topbar_black_bg);

        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getResources().getString(R.string.photo));

        txtAll = (TextView) findViewById(R.id.txt_listtop_1);
        txtAll.setText(getResources().getString(R.string.all));
        txtHot = (TextView) findViewById(R.id.txt_listtop_2);
        txtHot.setText(getResources().getString(R.string.hot));
        txtCar = (TextView) findViewById(R.id.txt_listtop_3);
        // txtCar.setText(getResources().getString(R.string.car));
        txtGirl = (TextView) findViewById(R.id.txt_listtop_4);
        // txtGirl.setText(getResources().getString(R.string.girl));
        txtPhotograph = (TextView) findViewById(R.id.txt_listtop_5);
        // txtPhotograph.setText(getResources().getString(R.string.photograph));
        txtFun = (TextView) findViewById(R.id.txt_listtop_6);
        // txtFun.setText(getResources().getString(R.string.fun));

        SharedPreferences preference = PreferenceManager
                .getDefaultSharedPreferences(PhotosActivity.this);
        String one = preference.getString(Preferences.PHOTO_ONE,
                getResources().getString(R.string.car));
        String two = preference.getString(Preferences.PHOTO_TWO,
                getResources().getString(R.string.girl));
        String three = preference.getString(Preferences.PHOTO_THREE,
                getResources().getString(R.string.photograph));
        String four = preference.getString(Preferences.PHOTO_FOUR,
                getResources().getString(R.string.fun));
        txtCar.setText(one);
        txtGirl.setText(two);
        txtPhotograph.setText(three);
        txtFun.setText(four);

        handler.sendEmptyMessage(MSG_GET_PHOTO_SORT);

        itemWidth = getScreenWidth() / 6;

        txtAll.setOnClickListener(new PhotosItemOnClickListener());
        txtHot.setOnClickListener(new PhotosItemOnClickListener());
        txtCar.setOnClickListener(new PhotosItemOnClickListener());
        txtGirl.setOnClickListener(new PhotosItemOnClickListener());
        txtPhotograph.setOnClickListener(new PhotosItemOnClickListener());
        txtFun.setOnClickListener(new PhotosItemOnClickListener());

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(itemWidth,
                LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        // set the activity of photos all
        intent = new Intent(PhotosActivity.this, Photos_AllActivity.class);
        relMain = (RelativeLayout) findViewById(R.id.rel_photos_main);
        vMain = getLocalActivityManager().startActivity("all", intent).getDecorView();
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        relMain.addView(vMain, params);
        setCurTxt(1);
    }

    private class PhotosItemOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.txt_listtop_1:
                    setCurTxt(1);
                    intent.setClass(PhotosActivity.this, Photos_AllActivity.class);
                    vMain = getLocalActivityManager().startActivity("all", intent).getDecorView();
                    break;

                case R.id.txt_listtop_2:
                    intent.setClass(PhotosActivity.this, HotPhotosActivity.class);
                    vMain = getLocalActivityManager().startActivity("hot", intent).getDecorView();
                    setCurTxt(2);
                    break;

                case R.id.txt_listtop_3:
                    intent.setClass(PhotosActivity.this, CarPhotoActivity.class);
                    if (sortList != null && sortList.size() > 0) {
                        intent.putExtra("id", sortList.get(0).getId());
                    }
                    vMain = getLocalActivityManager().startActivity("car", intent).getDecorView();
                    setCurTxt(3);
                    break;

                case R.id.txt_listtop_4:
                    intent.setClass(PhotosActivity.this, GirlPhotoActivity.class);
                    if (sortList != null && sortList.size() > 1) {
                        intent.putExtra("id", sortList.get(1).getId());
                    }
                    vMain = getLocalActivityManager().startActivity("girl", intent).getDecorView();
                    setCurTxt(4);
                    break;

                case R.id.txt_listtop_5:
                    intent.setClass(PhotosActivity.this, PhotographPhotoActivity.class);
                    if (sortList != null && sortList.size() > 2) {
                        intent.putExtra("id", sortList.get(2).getId());
                    }
                    vMain = getLocalActivityManager().startActivity("photograph", intent)
                            .getDecorView();
                    setCurTxt(5);
                    break;

                case R.id.txt_listtop_6:
                    intent.setClass(PhotosActivity.this, FunPhotoActivity.class);
                    if (sortList != null && sortList.size() > 3) {
                        intent.putExtra("id", sortList.get(3).getId());
                    }
                    vMain = getLocalActivityManager().startActivity("fun", intent).getDecorView();
                    setCurTxt(6);
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

        switch (i) {
            case 1:
                txtAll.setSelected(true);
                txtAll.setTextColor(Color.WHITE);
                txtHot.setSelected(false);
                txtHot.setTextColor(Color.BLACK);
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
                txtAll.setSelected(false);
                txtAll.setTextColor(Color.BLACK);
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
            case 3:
                txtAll.setSelected(false);
                txtAll.setTextColor(Color.BLACK);
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
            case 4:
                txtAll.setSelected(false);
                txtAll.setTextColor(Color.BLACK);
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
            case 5:
                txtAll.setSelected(false);
                txtAll.setTextColor(Color.BLACK);
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
            case 6:
                txtAll.setSelected(false);
                txtAll.setTextColor(Color.BLACK);
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

    public void showMessage() {

        btnMessage = (ImageButton) findViewById(R.id.btn_message);
        txtNum = (TextView) findViewById(R.id.txt_message_num);
        btnMessage.setOnClickListener(this);
        txtNum.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_message:
            case R.id.txt_message_num:
                Intent intent = new Intent(PhotosActivity.this, MessageActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    class GetPhotoSortTask extends AsyncTask<Void, Void, ArrayList<PhotoSort>> {

        Exception reason = null;

        @Override
        protected ArrayList<PhotoSort> doInBackground(Void... params) {

            try {
                sortList = wh_DMApi.getPhotoSort();
                return sortList;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<PhotoSort> result) {

            if (sortList != null && sortList.size() > 0) {

                String one = sortList.get(0).getTypename();
                String two = sortList.get(1).getTypename();
                String three = sortList.get(2).getTypename();
                String four = sortList.get(3).getTypename();
                txtCar.setText(one);
                txtGirl.setText(two);
                txtPhotograph.setText(three);
                txtFun.setText(four);

                Preferences.savePhotoType(PhotosActivity.this, one, two, three, four);
            }
            super.onPostExecute(result);
        }

    }

}
