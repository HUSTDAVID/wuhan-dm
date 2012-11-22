
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DM_MZine1Activity extends Activity {

    LinearLayout view1_linear1;
    LinearLayout view1_linear2;
    LinearLayout view1_linear3;
    LinearLayout view1_linear4;
    LinearLayout view1_linear5;

    TextView view1_view3_txtTile1;
    TextView view1_view3_txtTile2;
    TextView view1_view3_txtTile3;
    TextView view1_view3_txtTile4;
    TextView view1_view3_txtTile5;

    TextView view1_view3_txtBody1;
    TextView view1_view3_txtBody2;
    TextView view1_view3_txtBody3;
    TextView view1_view3_txtBody4;
    TextView view1_view3_txtBody5;

    LinearLayout view2_linear1;
    LinearLayout view2_linear2;
    LinearLayout view2_linear3;
    LinearLayout view2_linear4;
    LinearLayout view2_linear5;

    TextView view2_view3_txtTile1;
    TextView view2_view3_txtTile2;
    TextView view2_view3_txtTile3;
    TextView view2_view3_txtTile4;
    TextView view2_view3_txtTile5;

    TextView view2_view3_txtBody1;
    TextView view2_view3_txtBody2;
    TextView view2_view3_txtBody3;
    TextView view2_view3_txtBody4;
    TextView view2_view3_txtBody5;

    LinearLayout view3_linear1;
    LinearLayout view3_linear2;
    LinearLayout view3_linear3;
    LinearLayout view3_linear4;
    LinearLayout view3_linear5;

    TextView view3_txtTile1;
    TextView view3_txtTile2;
    TextView view3_txtTile3;
    TextView view3_txtTile4;
    TextView view3_txtTile5;

    TextView view3_txtBody1;
    TextView view3_txtBody2;
    TextView view3_txtBody3;
    TextView view3_txtBody4;
    TextView view3_txtBody5;

    private ArrayList<View> views;
    private View view1, view2, view3;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_mzine1);
        init();
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

    public void init() {

        LayoutInflater inflater = getLayoutInflater();
        views = new ArrayList<View>();
        view1 = inflater.inflate(R.layout.magzine1_style1, null);
        view2 = inflater.inflate(R.layout.magzine1_style2, null);
        view3 = inflater.inflate(R.layout.magzine1_style3, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);

        viewPager = (ViewPager) findViewById(R.id.v_Pager);
        viewPager.setAdapter(new MyPagerAdapter(views));
        viewPager.setCurrentItem(0);
        initView1();
        initView2();
        initView3();
    }

    private void initView1() {

        view1_linear1 = (LinearLayout) view1.findViewById(R.id.layout1_magazine_1);
        view1_linear2 = (LinearLayout) view1.findViewById(R.id.layout2_magazine_1);
        view1_linear3 = (LinearLayout) view1.findViewById(R.id.layout3_magazine_1);
        view1_linear4 = (LinearLayout) view1.findViewById(R.id.layout4_magazine_1);
        view1_linear5 = (LinearLayout) view1.findViewById(R.id.layout5_magazine_1);

        view1_view3_txtTile1 = (TextView) view1.findViewById(R.id.txt_magazine_1_title1);
        view1_view3_txtTile2 = (TextView) view1.findViewById(R.id.txt_magazine_1_title2);
        view1_view3_txtTile3 = (TextView) view1.findViewById(R.id.txt_magazine_1_title3);
        view1_view3_txtTile4 = (TextView) view1.findViewById(R.id.txt_magazine_1_title4);
        view1_view3_txtTile5 = (TextView) view1.findViewById(R.id.txt_magazine_1_title5);

        view1_linear1.setOnClickListener(listener1);
        view1_linear2.setOnClickListener(listener1);
        view1_linear3.setOnClickListener(listener1);
        view1_linear4.setOnClickListener(listener1);
        view1_linear5.setOnClickListener(listener1);

    }

    private void initView2() {

        view2_linear1 = (LinearLayout) view2.findViewById(R.id.layout1_magazine_2);
        view2_linear2 = (LinearLayout) view2.findViewById(R.id.layout2_magazine_2);
        view2_linear3 = (LinearLayout) view2.findViewById(R.id.layout3_magazine_2);
        view2_linear4 = (LinearLayout) view2.findViewById(R.id.layout4_magazine_2);
        view2_linear5 = (LinearLayout) view2.findViewById(R.id.layout5_magazine_2);

        view2_view3_txtTile1 = (TextView) view2.findViewById(R.id.txt_magazine_2_title1);
        view2_view3_txtTile2 = (TextView) view2.findViewById(R.id.txt_magazine_2_title2);
        view2_view3_txtTile3 = (TextView) view2.findViewById(R.id.txt_magazine_2_title3);
        view2_view3_txtTile4 = (TextView) view2.findViewById(R.id.txt_magazine_2_title4);
        view2_view3_txtTile5 = (TextView) view2.findViewById(R.id.txt_magazine_2_title5);

        view2_linear1.setOnClickListener(listener2);
        view2_linear2.setOnClickListener(listener2);
        view2_linear3.setOnClickListener(listener2);
        view2_linear4.setOnClickListener(listener2);
        view2_linear5.setOnClickListener(listener2);

    }

    public void initView3() {

        view3_linear1 = (LinearLayout) view3.findViewById(R.id.layout1_magazine_3);
        view3_linear2 = (LinearLayout) view3.findViewById(R.id.layout2_magazine_3);
        view3_linear3 = (LinearLayout) view3.findViewById(R.id.layout3_magazine_3);
        view3_linear4 = (LinearLayout) view3.findViewById(R.id.layout4_magazine_3);
        view3_linear5 = (LinearLayout) view3.findViewById(R.id.layout5_magazine_3);

        view3_txtTile1 = (TextView) view3.findViewById(R.id.txt_magazine_3_title1);
        view3_txtTile2 = (TextView) view3.findViewById(R.id.txt_magazine_3_title2);
        view3_txtTile3 = (TextView) view3.findViewById(R.id.txt_magazine_3_title3);
        view3_txtTile4 = (TextView) view3.findViewById(R.id.txt_magazine_3_title4);
        view3_txtTile5 = (TextView) view3.findViewById(R.id.txt_magazine_3_title5);

        view3_linear1.setOnClickListener(listener3);
        view3_linear2.setOnClickListener(listener3);
        view3_linear3.setOnClickListener(listener3);
        view3_linear4.setOnClickListener(listener3);
        view3_linear5.setOnClickListener(listener3);

    }

    public OnClickListener listener1 = new OnClickListener() {

        @Override
        public void onClick(View v) {

            int backColor = getResources().getColor(R.color.bg_clicked);
            switch (v.getId()) {
                case R.id.layout1_magazine_1:
                    view1_linear1.setBackgroundColor(backColor);
                    view1_view3_txtTile1.setTextColor(getResources().getColor(
                            R.color.mzine_txt_clicked));
                    break;
                case R.id.layout2_magazine_1:
                    view1_linear2.setBackgroundColor(backColor);
                    view1_view3_txtTile2.setTextColor(getResources().getColor(
                            R.color.mzine_txt_clicked));
                    break;
                case R.id.layout3_magazine_1:
                    view1_linear3.setBackgroundColor(backColor);
                    view1_view3_txtTile3.setTextColor(getResources().getColor(
                            R.color.mzine_txt_clicked));
                    break;
                case R.id.layout4_magazine_1:
                    view1_linear4.setBackgroundColor(backColor);
                    view1_view3_txtTile4.setTextColor(getResources().getColor(
                            R.color.mzine_txt_clicked));
                    break;
                case R.id.layout5_magazine_1:
                    view1_linear5.setBackgroundColor(backColor);
                    view1_view3_txtTile5.setTextColor(getResources().getColor(
                            R.color.mzine_txt_clicked));
                    break;
            }

        }
    };

    public OnClickListener listener2 = new OnClickListener() {
        @Override
        public void onClick(View v) {

            int backColor = getResources().getColor(R.color.bg_clicked);
            switch (v.getId()) {
                case R.id.layout1_magazine_2:
                    view2_linear1.setBackgroundColor(backColor);
                    view2_view3_txtTile1.setTextColor(getResources().getColor(
                            R.color.mzine_txt_clicked));
                    break;
                case R.id.layout2_magazine_2:
                    view2_linear2.setBackgroundColor(backColor);
                    view2_view3_txtTile2.setTextColor(getResources().getColor(
                            R.color.mzine_txt_clicked));
                    break;
                case R.id.layout3_magazine_2:
                    view2_linear3.setBackgroundColor(backColor);
                    view2_view3_txtTile3.setTextColor(getResources().getColor(
                            R.color.mzine_txt_clicked));
                    break;
                case R.id.layout4_magazine_2:
                    view2_linear4.setBackgroundColor(backColor);
                    view2_view3_txtTile4.setTextColor(getResources().getColor(
                            R.color.mzine_txt_clicked));
                    break;
                case R.id.layout5_magazine_2:
                    view2_linear5.setBackgroundColor(backColor);
                    view2_view3_txtTile5.setTextColor(getResources().getColor(
                            R.color.mzine_txt_clicked));
                    break;
            }

        }
    };

    public OnClickListener listener3 = new OnClickListener() {
        @Override
        public void onClick(View v) {

            int backColor = getResources().getColor(R.color.bg_clicked);

            switch (v.getId()) {
                case R.id.layout1_magazine_3:
                    view3_linear1.setBackgroundColor(backColor);
                    view3_txtTile1.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));

                    break;
                case R.id.layout2_magazine_3:
                    view3_linear2.setBackgroundColor(backColor);
                    view3_txtTile2.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));

                    break;
                case R.id.layout3_magazine_3:
                    view3_linear3.setBackgroundColor(backColor);
                    view3_txtTile3.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));

                    break;
                case R.id.layout4_magazine_3:
                    view3_linear4.setBackgroundColor(backColor);
                    view3_txtTile4.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));

                    break;
                case R.id.layout5_magazine_3:
                    view3_linear5.setBackgroundColor(backColor);
                    view3_txtTile5.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));

                    break;
            }

        }
    };

    private class MyPagerAdapter extends PagerAdapter {

        private final ArrayList<View> mListView;

        private MyPagerAdapter(ArrayList<View> list) {

            // TODO Auto-generated method stub
            this.mListView = list;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {

            // TODO Auto-generated method stub
            ((ViewGroup) arg0).removeView(mListView.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {

            // TODO Auto-generated method stub

        }

        @Override
        public int getCount() {

            // TODO Auto-generated method stub
            return mListView.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {

            // TODO Auto-generated method stub
            ((ViewGroup) arg0).addView(mListView.get(arg1), 0);
            return mListView.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            // TODO Auto-generated method stub
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {

            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

            // TODO Auto-generated method stub

        }

    }

}
