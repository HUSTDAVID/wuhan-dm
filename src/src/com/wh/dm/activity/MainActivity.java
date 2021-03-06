
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.Magazine;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.UrlImageViewHelper;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnTouchListener {

    /** GridView. */
    private LinearLayout linear;
    private DragGrid gridView;
    private TextView txt_page;
    private ScrollLayout scrollLayout;
    private ImageView delImage;
    private int delSid = 0;
    LinearLayout.LayoutParams param;
    Animation up, down;

    private ImageButton btn_share;
    private ImageButton btn_download;
    private ImageButton btn_add;
    private ImageButton btn_set;
    private LinearLayout layout_load;
    private ImageView load_close;
    private RelativeLayout relateBg;

    // quit
    private RelativeLayout relativeLayoutQuit;
    private Button btnQuitCacel;
    private Button btnQuitOk;
    // load
    private int totle = 0;
    private int cur = 0;

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
    private boolean unSub_result = false;

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
                    if (((WH_DMApp) getApplication()).isConnected()) {
                        delImage.startAnimation(down);
                        lists.get(Configure.curentPage).remove(Configure.removeItem);
                    } else {
                        delImage.startAnimation(down);
                    }

                    break;
                case REFRESH_GRID:
                    data.clear();
                    menu_init();
                    data.addAll(databaseImpl.getSubcribedMagazine());
                    initData();
                    cleanItems();
                    break;
                case UNSUBCRIBE:
                    if (unSubcribeTask != null) {
                        unSubcribeTask.cancel(true);
                        unSubcribeTask = null;
                    }
                    unSubcribeTask = new UnSubcribeTask();
                    unSubcribeTask.execute(lists.get(Configure.curentPage)
                            .get(Configure.removeItem).getSid());
                    break;
                case DownloadActivity.MSG_LOAD_ONE_IMAGE:
                    cur++;
                    totle = data.size();
                    if (cur == totle) {
                        UrlImageViewHelper.isLoad = false;
                        layout_load.setVisibility(View.GONE);
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
        totle = data.size();
        cur = 3;
        UrlImageViewHelper.isLoad = true;
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (data.size() == 3) {
                    layout_load.setVisibility(View.GONE);
                }
            }
        }, 5000);

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

    @Override
    public void onBackPressed() {

        if (relativeLayoutQuit.getVisibility() == View.GONE) {
            relativeLayoutQuit.setVisibility(View.VISIBLE);
        } else {
            relativeLayoutQuit.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {

        // databaseImpl.refreshMagazines(data);
        this.unregisterReceiver(mSubChangeReceiver);
        super.onDestroy();
    }

    public void menu_init() {

        Magazine Magazine1 = new Magazine();
        String name1 = getResources().getString(R.string.temp1);
        Magazine1.setName(name1);
        Magazine1.setDrawableId(R.drawable.t1);
        Magazine1.setId(1);

        Magazine Magazine2 = new Magazine();
        String name3 = getResources().getString(R.string.temp3);
        Magazine2.setName(name3);
        Magazine2.setDrawableId(R.drawable.t3);
        Magazine2.setId(2);

        Magazine Magazine3 = new Magazine();
        String name2 = getResources().getString(R.string.temp2);
        Magazine3.setName(name2);
        Magazine3.setDrawableId(R.drawable.t2);
        Magazine3.setId(3);

        data.add(Magazine1);
        data.add(Magazine2);
        data.add(Magazine3);

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

        // load layout
        layout_load = (LinearLayout) findViewById(R.id.home_load);
        load_close = (ImageView) findViewById(R.id.btn_home_load_close);
        load_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                layout_load.setVisibility(View.GONE);

            }
        });

        // quit
        relativeLayoutQuit = (RelativeLayout) findViewById(R.id.linearlayout_quit);
        relativeLayoutQuit.setOnTouchListener(this);
        btnQuitCacel = (Button) findViewById(R.id.btn_quit_cacel);
        btnQuitOk = (Button) findViewById(R.id.btn_quit_ok);
        btnQuitOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                MainActivity.this.finish();

            }
        });

        btnQuitCacel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                relativeLayoutQuit.setVisibility(View.GONE);

            }
        });

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        scrollLayout = (ScrollLayout) findViewById(R.id.views);
        scrollLayout.setOnTouchListener(this);
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
        btn_share = (ImageButton) findViewById(R.id.btn_apk_share);
        btn_share.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                intent.putExtra("share", getResources().getString(R.string.share_apk));
                startActivity(intent);
            }
        });

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
                    : PAGE_SIZE * (i + 1)); j++) {
                lists.get(i).add(data.get(j));
            }
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
        gridView.setAdapter(new DragGridAdapter(MainActivity.this, lists.get(i), handler));
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
                        Intent intent_photo = new Intent(MainActivity.this, PhotosActivity.class);
                        startActivity(intent_photo);

                        break;
                    case 3:
                        Intent intent_collect = new Intent(MainActivity.this, MessageActivity.class);
                        startActivity(intent_collect);
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

                // First three can`t be moved
                if (Configure.curentPage == 0 && (to == 0 || to == 1 || to == 2)) {
                    ((DragGridAdapter) gridviews.get(Configure.curentPage - count).getAdapter())
                            .notifyDataSetChanged();
                    ((DragGridAdapter) gridviews.get(Configure.curentPage).getAdapter())
                            .notifyDataSetChanged();
                    return;
                }

                // If move out of size of target page , use last one
                if (to > lists.get(Configure.curentPage).size() - 1) {
                    to = lists.get(Configure.curentPage).size() - 1;
                }

                Magazine toString = lists.get(Configure.curentPage - count).get(from);

                lists.get(Configure.curentPage - count).add(from,
                        lists.get(Configure.curentPage).get(to));
                lists.get(Configure.curentPage - count).remove(from + 1);
                lists.get(Configure.curentPage).add(to, toString);
                lists.get(Configure.curentPage).remove(to + 1);

                ((DragGridAdapter) gridviews.get(Configure.curentPage - count).getAdapter())
                        .notifyDataSetChanged();
                ((DragGridAdapter) gridviews.get(Configure.curentPage).getAdapter())
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

                txt_page.setText(page + 1 + "");
                txt_page.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                        R.anim.scale_out));
            }

        });
        txt_page.startAnimation(a);

    }

    private class UnSubcribeTask extends AsyncTask<Integer, Void, Integer> {
        Exception reason = null;

        @Override
        protected Integer doInBackground(Integer... params) {

            unSub_result = false;
            try {
                delSid = params[0];
                unSub_result = wh_dmApi.unsubcribe(params[0]);
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
                databaseImpl.deleteOneLoadInfo(delSid);
                sendBroadcast(new Intent(WH_DMApp.INTENT_ACTION_SUBCRIBE_CHANGE));
                unSub_result = false;
                NotificationUtil
                        .showShortToast(getString(R.string.unsub_suceed), MainActivity.this);
            } else {
                NotificationUtil.showShortToast(getString(R.string.unsub_fail), MainActivity.this);
            }
            super.onPostExecute(result);
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (relativeLayoutQuit.getVisibility() == View.VISIBLE) {
            relativeLayoutQuit.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

}
