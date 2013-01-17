
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
import android.view.Gravity;
import android.view.LayoutInflater;
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

public class PhotosActivity extends ActivityGroup implements OnClickListener {

    private RelativeLayout relMain;
    private LayoutParams params = null;
    private ImageButton btnMessage;
    private TextView txtNum;
    private View vMain;
    private int itemWidth = 0;
    Intent intent = null;
    private LinearLayout menuLinerLayout;
    private ArrayList<TextView> menuList;
    private String[] photoSortNames;
    private String[] photoIds;

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

        // add list top
        menuList = new ArrayList<TextView>();
        menuLinerLayout = (LinearLayout) findViewById(R.id.linearLayoutMenu);
        menuLinerLayout.setOrientation(LinearLayout.HORIZONTAL);
        // ≤Œ ˝…Ë÷√
        LinearLayout.LayoutParams menuLinerLayoutParames = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        menuLinerLayoutParames.gravity = Gravity.CENTER_HORIZONTAL;

        SharedPreferences preference = PreferenceManager
                .getDefaultSharedPreferences(PhotosActivity.this);
        String photoAllSortName = preference.getString(Preferences.PHOTO_ALL_SORT,
                getString(R.string.photo_all_sort));
        String photoAllIds = preference.getString(Preferences.PHOTO_ALL_SORT,
                getString(R.string.photo_all_id));
        photoSortNames = photoAllSortName.split("\\:");
        photoIds = photoAllIds.split("\\:");
        //
        TextView txtHot = new TextView(this);
        txtHot = initMenu(txtHot, getResources().getString(R.string.hot));
        txtHot.setId(1);
        txtHot.setOnClickListener(new PhotosItemOnClickListener());
        menuList.add(txtHot);
        menuLinerLayout.addView(txtHot);

        for (int i = 0; i < photoSortNames.length; i++) {
            TextView txtView = new TextView(this);
            txtView = initMenu(txtView, photoSortNames[i]);
            txtView.setId(i + 2);
            txtView.setOnClickListener(new PhotosItemOnClickListener());
            menuList.add(txtView);
            menuLinerLayout.addView(txtView);
        }

        handler.sendEmptyMessage(MSG_GET_PHOTO_SORT);

        itemWidth = getScreenWidth() / 5;

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(itemWidth,
                LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        // set the activity of photos all
        intent = new Intent(PhotosActivity.this, HotPhotosActivity.class);
        relMain = (RelativeLayout) findViewById(R.id.rel_photos_main);
        vMain = getLocalActivityManager().startActivity("hot", intent).getDecorView();
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        relMain.addView(vMain, params);
        setCurTxt(1);
    }

    private class PhotosItemOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case 1:
                    intent.setClass(PhotosActivity.this, HotPhotosActivity.class);
                    vMain = getLocalActivityManager().startActivity("hot", intent).getDecorView();
                    setCurTxt(1);
                    break;

                case 2:
                    intent.setClass(PhotosActivity.this, CarPhotoActivity.class);
                    intent.putExtra("id", photoIds[0]);
                    vMain = getLocalActivityManager().startActivity("car", intent).getDecorView();
                    setCurTxt(2);
                    break;

                case 3:
                    intent.setClass(PhotosActivity.this, GirlPhotoActivity.class);
                    intent.putExtra("id", photoIds[1]);
                    vMain = getLocalActivityManager().startActivity("girl", intent).getDecorView();
                    setCurTxt(3);
                    break;

                case 4:
                    intent.setClass(PhotosActivity.this, PhotographPhotoActivity.class);
                    intent.putExtra("id", photoIds[2]);
                    vMain = getLocalActivityManager().startActivity("photograph", intent)
                            .getDecorView();
                    setCurTxt(4);
                    break;

                case 5:
                    intent.setClass(PhotosActivity.this, FunPhotoActivity.class);
                    intent.putExtra("id", photoIds[3]);
                    vMain = getLocalActivityManager().startActivity("fun", intent).getDecorView();
                    setCurTxt(5);
                    break;
                default:
                    int id = v.getId();
                    setCurTxt(id);
                    getLocalActivityManager().destroyActivity("other", true);
                    intent.setClass(PhotosActivity.this, PhototOtherSortActivity.class);
                    intent.putExtra("id", photoIds[id - 2]);
                    vMain = getLocalActivityManager().startActivity("other", intent).getDecorView();
                    break;
            }
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
        int num = Preferences.getMsgNum(PhotosActivity.this);
        txtNum.setText("" + num);

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

                String photoAllSortName = "";
                String photoAllId = "";
                int size = result.size();
                int length = photoSortNames.length;
                // index 0 is headline
                for (int i = 0; i < length; i++) {
                    menuList.get(i + 1).setText(result.get(i).getTypename());
                }
                for (int i = 0; i < size; i++) {
                    photoAllSortName += result.get(i).getTypename();
                    photoAllId += result.get(i).getId();
                    photoAllSortName += ":";
                    photoAllId += ":";
                }
                photoIds = photoAllId.split("\\:");
                if (size > length) {

                    for (int i = length; i < size; i++) {
                        TextView txtOther = new TextView(PhotosActivity.this);
                        txtOther = initMenu(txtOther, result.get(i).getTypename());
                        txtOther.setId(i + 2);
                        txtOther.setOnClickListener(new PhotosItemOnClickListener());
                        menuLinerLayout.addView(txtOther);
                        menuList.add(txtOther);

                    }
                } else if (size < length) {

                    for (int i = size + 1; i <= length; i++) {
                        menuLinerLayout.removeViewAt(i);
                    }
                }
                Preferences.savePhotoType(PhotosActivity.this, photoAllSortName, photoAllId);
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
