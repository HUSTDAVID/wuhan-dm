
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.Magazine;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.Configure;
import com.wh.dm.widget.DragGrid;
import com.wh.dm.widget.DragGridAdapter;
import com.wh.dm.widget.ScrollLayout;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    /** GridView. */
    private LinearLayout linear;
    private DragGrid gridView;
    private TextView txt_page;
    private ScrollLayout scrollLayout;
    private ImageView delImage;
    LinearLayout.LayoutParams param;
    Animation up, down;

    private ImageButton btn_download;
    private ImageButton btn_add;
    private ImageButton btn_set;

    public static final int PAGE_SIZE = 8;
    public static final int SLIDE_GRID = 0;
    public static final int DEL_UP = 1;
    public static final int DEL_DARK = 2;
    public static final int DEL_LIGHT = 3;
    public static final int DEL_DOWN = 4;
    public static final int DEL_GRID = 5;
    public static final int REFRESH_GRID = 6;
    public static final int UNSUBCRIBE = 7;
    private UnSubcribeTask unSubcribeTask = null;

    // DM VERSION
    public static final int DM_FULL = 1;
    public static final int DM_PICS_TXT = 0;

    private ArrayList<DragGrid> gridviews = new ArrayList<DragGrid>();
    private ArrayList<ArrayList<Magazine>> lists = new ArrayList<ArrayList<Magazine>>();
    private ArrayList<Magazine> data = new ArrayList<Magazine>();
    private DatabaseImpl databaseImpl;
    private WH_DMApi wh_dmApi;

    boolean isClean = false;
    Vibrator vibrator;
    int rockCount = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SLIDE_GRID:
                    Configure.isChangingPage = false;
                    break;
                case DEL_UP:
                    delImage.setBackgroundResource(R.drawable.del);
                    delImage.setVisibility(0);
                    delImage.startAnimation(up);
                    break;
                case DEL_DARK:
                    delImage.setBackgroundResource(R.drawable.del_check);
                    Configure.isDelDark = true;
                    break;
                case DEL_LIGHT:
                    delImage.setBackgroundResource(R.drawable.del);
                    Configure.isDelDark = false;
                    break;
                case DEL_DOWN:
                    delImage.startAnimation(down);
                    break;

                case DEL_GRID:

                    delImage.startAnimation(down);
                    lists.get(Configure.curentPage).remove(Configure.removeItem);
                    data.remove(Configure.removeItem);
                    cleanItems();
                    ((DragGridAdapter) ((gridviews.get(Configure.curentPage)).getAdapter()))
                            .notifyDataSetChanged();

                    break;
                case REFRESH_GRID:
                    data.clear();
                    menu_init();
                    data.addAll(databaseImpl.getSubcribedMagazine());
                    initData();
                    cleanItems();
                    ((DragGridAdapter) ((gridviews.get(Configure.curentPage)).getAdapter()))
                            .notifyDataSetChanged();
                    break;
                case UNSUBCRIBE:
                    if (unSubcribeTask != null) {
                        unSubcribeTask.cancel(true);
                        unSubcribeTask = null;
                    }
                    if (WH_DMApp.isLogin) {
                        unSubcribeTask = new UnSubcribeTask();
                        unSubcribeTask.execute(data.get(Configure.removeItem).getSid());
                    } else {
                        NotificationUtil.showShortToast(getString(R.string.please_login),
                                MainActivity.this);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    break;

            }
            super.handleMessage(msg);
        }
    };

    private final BroadcastReceiver mSubChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            handler.sendEmptyMessage(REFRESH_GRID);
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_main);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        init();
        menu_init();
        data.addAll(databaseImpl.getSubcribedMagazine());
        initData();
        for (int i = 0; i < Configure.countPages; i++) {
            scrollLayout.addView(addGridView(i));
        }

        scrollLayout.setPageListener(new ScrollLayout.PageListener() {
            @Override
            public void page(int page) {

                setCurPage(page);
            }
        });

        runAnimation();
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

    public void menu_init() {

        Magazine Magazine1 = new Magazine();
        String name1 = getResources().getString(R.string.temp1);
        Magazine1.setName(name1);
        Magazine1.setDrawableId(R.drawable.t1);
        Magazine1.setId(1);

        Magazine Magazine2 = new Magazine();
        String name2 = getResources().getString(R.string.temp2);
        Magazine2.setName(name2);
        Magazine2.setDrawableId(R.drawable.t2);
        Magazine2.setId(2);

        data.add(Magazine1);
        data.add(Magazine2);

    }

    public void init() {

        // set wakeLock
        WH_DMApp whApp = (WH_DMApp) getApplication();
        wh_dmApi = whApp.getWH_DMApi();
        databaseImpl = whApp.getDatabase();
        whApp.mContext = this;
        SharedPreferences sharePreference = getSharedPreferences("com.wh.dm_preferences", 1);
        // when the checkboxpreference is seleted ,it return false
        boolean isWake = sharePreference.getBoolean("wake_lock", true);
        if (isWake) {
            whApp.releaseWakeLock();
        } else {
            whApp.acquireWakeLock();
        }

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        scrollLayout = (ScrollLayout) findViewById(R.id.views);
        delImage = (ImageView) findViewById(R.id.dels);
        txt_page = (TextView) findViewById(R.id.tv_page);
        txt_page.setText("1");
        com.wh.dm.widget.Configure.init(MainActivity.this);
        param = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
                android.view.ViewGroup.LayoutParams.FILL_PARENT);

        param.rightMargin = dip2px(55);
        param.leftMargin = 20;
        if (gridView != null) {
            scrollLayout.removeAllViews();
        }
        btn_download = (ImageButton) findViewById(R.id.btn_download);
        btn_add = (ImageButton) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SubscribeActivity.class);
                startActivity(intent);

            }

        });
        btn_set = (ImageButton) findViewById(R.id.btn_set);
        btn_set.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

            }

        });
        btn_download = (ImageButton) findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intent);
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(WH_DMApp.INTENT_ACTION_SUBCRIBE_CHANGE);
        registerReceiver(mSubChangeReceiver, filter);
    }

    public int dip2px(int dip) {

        final float scale = Configure.screenDensity;
        return (int) (dip * scale + 0.5f);
    }

    public void initData() {

        Configure.countPages = (int) Math.ceil(data.size() / (float) PAGE_SIZE);
        lists = new ArrayList<ArrayList<Magazine>>();
        for (int i = 0; i < Configure.countPages; i++) {
            lists.add(new ArrayList<Magazine>());
            for (int j = PAGE_SIZE * i; j < (PAGE_SIZE * (i + 1) > data.size() ? data.size()
                    : PAGE_SIZE * (i + 1)); j++)
                lists.get(i).add(data.get(j));
        }

    }

    public void cleanItems() {

        scrollLayout.removeAllViews();
        data = new ArrayList<Magazine>();
        for (int i = 0; i < lists.size(); i++) {
            for (int j = 0; j < lists.get(i).size(); j++) {
                if (lists.get(i).get(j) != null) {
                    data.add(lists.get(i).get(j));
                }
            }
        }
        initData();
        gridviews = new ArrayList<DragGrid>();
        for (int i = 0; i < Configure.countPages; i++) {
            scrollLayout.addView(addGridView(i));
        }
        isClean = false;
    }

    public LinearLayout addGridView(final int i) {

        linear = new LinearLayout(MainActivity.this);
        gridView = new DragGrid(MainActivity.this);
        gridView.setAdapter(new DragGridAdapter(MainActivity.this, lists.get(i)));
        gridView.setNumColumns(2);
        gridView.setHorizontalSpacing(0);
        gridView.setVerticalSpacing(0);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                Bundle bundle = new Bundle();
                switch (lists.get(i).get(pos).getId()) {
                    case 1:
                        Intent intent_tab1 = new Intent(MainActivity.this, DM_Tab_1Activity.class);
                        startActivity(intent_tab1);
                        break;
                    case 2:
                        Intent intent_collect = new Intent(MainActivity.this, CollectActivity.class);
                        startActivity(intent_collect);
                        break;
                    case 3:
                        Intent intent_pic_txt = new Intent(MainActivity.this,
                                DM_Tab_2Activity.class);
                        bundle.putInt("dm", DM_PICS_TXT);
                        intent_pic_txt.putExtras(bundle);
                        startActivity(intent_pic_txt);
                        break;
                    case 4:
                        Intent intent_tab_full = new Intent(MainActivity.this,
                                DM_Tab_2Activity.class);
                        bundle.putInt("dm", DM_FULL);
                        intent_tab_full.putExtras(bundle);
                        startActivity(intent_tab_full);
                        break;
                    default:
                        Intent intent_magazine = new Intent(MainActivity.this,
                                DM_Tab_2Activity.class);
                        Magazine magazine = lists.get(i).get(pos);
                        bundle.putInt("dm", magazine.getTemplate());
                        bundle.putInt("sid", magazine.getSid());
                        intent_magazine.putExtras(bundle);
                        startActivity(intent_magazine);
                        break;
                }
            }
        });
        gridView.setSelector(R.anim.grid_light);
        gridView.setPageListener(new DragGrid.G_PageListener() {
            @Override
            public void page(int cases, int page) {

                switch (cases) {
                    case SLIDE_GRID:
                        scrollLayout.snapToScreen(page);
                        setCurPage(page);
                        handler.sendEmptyMessage(SLIDE_GRID);
                        break;
                    case DEL_UP:
                        handler.sendEmptyMessage(DEL_UP);
                        break;

                    case DEL_DARK:
                        handler.sendEmptyMessage(DEL_DARK);
                        break;
                    case DEL_LIGHT:
                        handler.sendEmptyMessage(DEL_LIGHT);
                        break;

                    case DEL_DOWN:
                        handler.sendEmptyMessage(DEL_DOWN);
                        break;

                    case DEL_GRID:
                        handler.sendEmptyMessage(UNSUBCRIBE);
                        handler.sendEmptyMessage(DEL_GRID);
                        break;
                    default:
                        delImage.startAnimation(down);
                        break;
                }
            }
        });
        gridView.setOnItemChangeListener(new DragGrid.G_ItemChangeListener() {
            @Override
            public void change(int from, int to, int count) {

                Magazine toString = lists.get(Configure.curentPage - count).get(from);

                lists.get(Configure.curentPage - count).add(from,
                        lists.get(Configure.curentPage).get(to));
                lists.get(Configure.curentPage - count).remove(from + 1);
                lists.get(Configure.curentPage).add(to, toString);
                lists.get(Configure.curentPage).remove(to + 1);

                ((DragGridAdapter) ((gridviews.get(Configure.curentPage - count)).getAdapter()))
                        .notifyDataSetChanged();
                ((DragGridAdapter) ((gridviews.get(Configure.curentPage)).getAdapter()))
                        .notifyDataSetChanged();
            }
        });
        gridviews.add(gridView);
        LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT,
                android.view.ViewGroup.LayoutParams.FILL_PARENT);
        par.width = Configure.screenWidth / 2 - 10;
        par.height = Configure.screenHeight / 4 - 10;
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setLayoutParams(par);

        linear.addView(gridviews.get(i), param);

        return linear;
    }

    public void runAnimation() {

        down = AnimationUtils.loadAnimation(MainActivity.this, R.anim.del_down);
        up = AnimationUtils.loadAnimation(MainActivity.this, R.anim.del_up);
        down.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {

                // TODO Auto-generated method stub
                delImage.setVisibility(8);
            }
        });
    }

    public void setCurPage(final int page) {

        Animation a = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_in);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                txt_page.setText((page + 1) + "");
                txt_page.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                        R.anim.scale_out));
            }

        });
        txt_page.startAnimation(a);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private class UnSubcribeTask extends AsyncTask<Integer, Void, Integer> {
        Exception reason = null;

        @Override
        protected Integer doInBackground(Integer... params) {

            boolean result = false;
            try {
                result = wh_dmApi.unsubcribe(params[0]);
                return params[0];
            } catch (Exception e) {
                reason = e;
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (result != 0) {
                databaseImpl.delMagazine(result);
                sendBroadcast(new Intent(WH_DMApp.INTENT_ACTION_SUBCRIBE_CHANGE));

            } else {
                NotificationUtil.showShortToast(getString(R.string.unsub_fail), MainActivity.this);
            }
            super.onPostExecute(result);
        }

    }

}
