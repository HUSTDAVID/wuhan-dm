
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.type.Cover;
import com.wh.dm.widget.Configure;
import com.wh.dm.widget.DragGrid;
import com.wh.dm.widget.DragGridAdapter;
import com.wh.dm.widget.ScrollLayout;

import android.app.Activity;
import android.content.Intent;
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

    // DM VERSION
    public static final int DM_FULL = 0;
    public static final int DM_PICS = 1;
    public static final int DM_PICS_TXT = 2;
    public static final int DM_PICS_TXT2 = 3;
    public static final int DM_PICS_TXT3 = 4;

    ArrayList<DragGrid> gridviews = new ArrayList<DragGrid>();
    ArrayList<ArrayList<Cover>> lists = new ArrayList<ArrayList<Cover>>();
    ArrayList<Cover> data = new ArrayList<Cover>();

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
                default:
                    break;

            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        menu_init();
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

    public void menu_init() {

        Cover cover1 = new Cover();
        String name1 = getResources().getString(R.string.temp1);
        cover1.setName(name1);
        cover1.setDrawableId(R.drawable.t1);
        cover1.setId(0);

        Cover cover2 = new Cover();
        String name2 = getResources().getString(R.string.temp2);
        cover2.setName(name2);
        cover2.setDrawableId(R.drawable.t2);
        cover2.setId(1);

        Cover cover3 = new Cover();
        String name3 = getResources().getString(R.string.temp3);
        cover3.setName(name3);
        cover3.setDrawableId(R.drawable.t3);
        cover3.setId(2);

        Cover cover4 = new Cover();
        String name4 = getResources().getString(R.string.temp4);
        cover4.setName(name4);
        cover4.setDrawableId(R.drawable.t4);
        cover4.setId(3);

        Cover cover5 = new Cover();
        String name5 = getResources().getString(R.string.temp5);
        cover5.setName(name5);
        cover5.setDrawableId(R.drawable.t5);
        cover5.setId(4);

        Cover cover6 = new Cover();
        String name6 = getResources().getString(R.string.temp6);
        cover6.setName(name6);
        cover6.setDrawableId(R.drawable.t6);
        cover6.setId(5);

        Cover cover7 = new Cover();
        String name7 = getResources().getString(R.string.temp7);
        cover7.setName(name7);
        cover7.setDrawableId(R.drawable.t7);
        cover7.setId(6);

        Cover cover8 = new Cover();
        String name8 = getResources().getString(R.string.temp8);
        cover8.setName(name8);
        cover8.setDrawableId(R.drawable.t8);
        cover8.setId(7);

        Cover cover9 = new Cover();
        String name9 = getResources().getString(R.string.temp9);
        cover9.setName(name9);
        cover9.setDrawableId(R.drawable.t9);
        cover9.setId(8);

        Cover cover10 = new Cover();
        String name10 = getResources().getString(R.string.temp10);
        cover10.setName(name10);
        cover10.setDrawableId(R.drawable.t10);
        cover10.setId(9);

        Cover cover11 = new Cover();
        String name11 = getResources().getString(R.string.temp11);
        cover11.setName(name11);
        cover11.setDrawableId(R.drawable.t11);
        cover11.setId(10);

        Cover cover12 = new Cover();
        String name12 = getResources().getString(R.string.temp12);
        cover12.setName(name12);
        cover12.setDrawableId(R.drawable.t12);
        cover12.setId(11);

        Cover cover13 = new Cover();
        String name13 = getResources().getString(R.string.temp13);
        cover13.setName(name13);
        cover13.setDrawableId(R.drawable.t13);
        cover13.setId(12);

        Cover cover14 = new Cover();
        String name14 = getResources().getString(R.string.temp14);
        cover14.setName(name14);
        cover14.setDrawableId(R.drawable.t14);
        cover14.setId(13);

        Cover cover15 = new Cover();
        String name15 = getResources().getString(R.string.temp15);
        cover15.setName(name15);
        cover15.setDrawableId(R.drawable.t15);
        cover15.setId(15);

        Cover cover16 = new Cover();
        String name16 = getResources().getString(R.string.temp16);
        cover16.setName(name16);
        cover16.setDrawableId(R.drawable.t16);
        cover16.setId(16);

        data.add(cover1);
        data.add(cover2);
        data.add(cover3);
        data.add(cover4);
        data.add(cover5);
        data.add(cover6);
        data.add(cover7);
        data.add(cover8);
        data.add(cover9);
        data.add(cover10);
        data.add(cover11);
        data.add(cover12);
        data.add(cover13);
        data.add(cover14);
        data.add(cover15);
        data.add(cover16);
    }

    public void init() {

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
        btn_set = (ImageButton) findViewById(R.id.btn_set);
        btn_set.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

            }

        });
    }

    public int dip2px(int dip) {

        final float scale = Configure.screenDensity;
        return (int) (dip * scale + 0.5f);
    }

    public void initData() {

        Configure.countPages = (int) Math.ceil(data.size() / (float) PAGE_SIZE);
        lists = new ArrayList<ArrayList<Cover>>();
        for (int i = 0; i < Configure.countPages; i++) {
            lists.add(new ArrayList<Cover>());
            for (int j = PAGE_SIZE * i; j < (PAGE_SIZE * (i + 1) > data.size() ? data.size()
                    : PAGE_SIZE * (i + 1)); j++)
                lists.get(i).add(data.get(j));
        }

    }

    public void cleanItems() {

        scrollLayout.removeAllViews();
        data = new ArrayList<Cover>();
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
                    case 0:
                        Intent intent_tab1 = new Intent(MainActivity.this, DM_Tab_1Activity.class);
                        startActivity(intent_tab1);
                        break;
                    case 1:
                        Intent intent_collect = new Intent(MainActivity.this, CollectActivity.class);
                        startActivity(intent_collect);
                        break;
                    case 2:
                        Intent intent_pic_txt = new Intent(MainActivity.this,
                                DM_Tab_2Activity.class);
                        bundle.putInt("dm", DM_PICS_TXT);
                        intent_pic_txt.putExtras(bundle);
                        startActivity(intent_pic_txt);
                        break;
                    case 3:
                        Intent intent_tab_full = new Intent(MainActivity.this,
                                DM_Tab_2Activity.class);
                        bundle.putInt("dm", DM_FULL);
                        intent_tab_full.putExtras(bundle);
                        startActivity(intent_tab_full);
                        break;

                    case 4:
                        Intent intent_pic_txt2 = new Intent(MainActivity.this,
                                DM_Tab_2Activity.class);
                        bundle.putInt("dm", DM_PICS_TXT2);
                        intent_pic_txt2.putExtras(bundle);
                        startActivity(intent_pic_txt2);
                        break;
                    case 5:

                        /*
                         * Intent intent_pic_txt3 = new
                         * Intent(MainActivity.this, LoginActivity.class);
                         * bundle.putInt("dm", DM_PICS_TXT3);
                         * intent_pic_txt3.putExtras(bundle);
                         * startActivity(intent_pic_txt3);
                         */

                        break;

                    case 6:

                        break;
                    case 7:

                        break;
                    default:
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

                Cover toString = lists.get(Configure.curentPage - count).get(from);

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

}
