
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.type.DM;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    /** GridView. */
    private LinearLayout linear;
    private DragGrid gridView;
    private ScrollLayout scrollLayout;

    TextView txt_page;
    private ImageView delImage;
    LinearLayout.LayoutParams param;
    Animation up, down;

    public static final int PAGE_SIZE = 8;
    public static final int SLIDE_GRID = 0;
    public static final int DEL_UP = 1;
    public static final int DEL_DARK = 2;
    public static final int DEL_LIGHT = 3;
    public static final int DEL_DOWN = 4;
    public static final int DEL_GRID = 5;

    ArrayList<DragGrid> gridviews = new ArrayList<DragGrid>();
    ArrayList<ArrayList<DM>> lists = new ArrayList<ArrayList<DM>>();
    ArrayList<DM> data = new ArrayList<DM>();

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
        for (int i = 0; i < 22; i++) {
            DM dm = new DM();
            dm.setDM("" + i);
            data.add(dm);
        }

        init();
        initData();
        init_default();
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
    }

    public int dip2px(float dpValue) {

        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void initData() {

        Configure.countPages = (int) Math.ceil(data.size() / (float) PAGE_SIZE);
        lists = new ArrayList<ArrayList<DM>>();
        for (int i = 0; i < Configure.countPages; i++) {
            lists.add(new ArrayList<DM>());
            for (int j = PAGE_SIZE * i; j < (PAGE_SIZE * (i + 1) > data.size() ? data.size()
                    : PAGE_SIZE * (i + 1)); j++)
                lists.get(i).add(data.get(j));
        }

    }

    public void init_default() {

        lists.get(0).get(0).setDM(getString(R.string.favorites));
        lists.get(0).get(0).setId(R.drawable.love);
        lists.get(0).get(0).setRed(true);
        lists.get(0).get(1).setDM(getString(R.string.collections));
        lists.get(0).get(1).setId(R.drawable.collect);
        lists.get(0).get(1).setRed(true);

        lists.get(0).get(2).setDM(getString(R.string.temp1));
        lists.get(0).get(2).setId(R.drawable.temp1);
        lists.get(0).get(3).setDM(getString(R.string.temp2));
        lists.get(0).get(3).setId(R.drawable.temp2);
        lists.get(0).get(4).setDM(getString(R.string.temp3));
        lists.get(0).get(4).setId(R.drawable.temp3);
        lists.get(0).get(5).setDM(getString(R.string.temp4));
        lists.get(0).get(5).setId(R.drawable.temp4);
    }

    public void cleanItems() {

        scrollLayout.removeAllViews();
        data = new ArrayList<DM>();
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

    public LinearLayout addGridView(int i) {

        linear = new LinearLayout(MainActivity.this);
        gridView = new DragGrid(MainActivity.this);
        gridView.setAdapter(new DragGridAdapter(MainActivity.this, lists.get(i)));
        gridView.setNumColumns(2);
        gridView.setHorizontalSpacing(0);
        gridView.setVerticalSpacing(0);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                switch (pos) {
                    case 0:
                        Intent it0 = new Intent(MainActivity.this, DM_Tab_1Activity.class);
                        startActivity(it0);
                        break;
                    case 1:
                        Intent it1 = new Intent(MainActivity.this, DM_Tab_2Activity.class);
                        startActivity(it1);
                        break;

                    case 2:
                        Intent it2 = new Intent(MainActivity.this, DM_MzineFullActivity.class);
                        startActivity(it2);
                        break;
                    case 3:
                        Intent it3 = new Intent(MainActivity.this, DM_MzineLongActivity.class);
                        startActivity(it3);
                        break;
                    case 4:
                        Intent it4 = new Intent(MainActivity.this, DM_MzineImgActivity.class);
                        startActivity(it4);
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

                DM toString = lists.get(Configure.curentPage - count).get(from);

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

                // TODO Auto-generated method stub
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
