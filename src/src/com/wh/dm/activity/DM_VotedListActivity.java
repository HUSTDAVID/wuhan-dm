
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DM_VotedListActivity extends Activity {
    private ViewPager viewPager;
    private ArrayList<View> pageViews;
    private ImageView imageView;
    private ImageView[] imageViews;

    private ViewGroup main;

    private ViewGroup group;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.dm_voteitem1, null);
        View v2 = inflater.inflate(R.layout.dm_voteitem2, null);
        View v3 = inflater.inflate(R.layout.dm_voteitem3, null);
        View v4 = inflater.inflate(R.layout.dm_voteitem4, null);
        View v5 = inflater.inflate(R.layout.dm_voteitem5, null);
        View v6 = inflater.inflate(R.layout.dm_voteitem6, null);
        View v7 = inflater.inflate(R.layout.dm_voteitem7, null);

        pageViews = new ArrayList<View>();
        pageViews.add(v1);
        pageViews.add(v2);
        pageViews.add(v3);
        pageViews.add(v4);
        pageViews.add(v5);
        pageViews.add(v6);
        pageViews.add(v7);

        imageViews = new ImageView[pageViews.size()];
        main = (ViewGroup) inflater.inflate(R.layout.activity_votemain, null);

        group = (ViewGroup) main.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) main.findViewById(R.id.guidePages);

        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(DM_VotedListActivity.this);
            imageView.setLayoutParams(new LayoutParams(20, 20));
            imageView.setPadding(20, 0, 20, 0);
            imageViews[i] = imageView;

            if (i == 0) {

                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);
            }

            group.addView(imageViews[i]);
        }

        setContentView(main);

        tv1 = (TextView) v1.findViewById(R.id.vote_list_1);
        tv1.setOnClickListener(new TextViewOnClickListener());
        tv2 = (TextView) v2.findViewById(R.id.textView2);
        tv2.setOnClickListener(new TextViewOnClickListener());
        tv3 = (TextView) v3.findViewById(R.id.textView3);
        tv3.setOnClickListener(new TextViewOnClickListener());
        tv4 = (TextView) v4.findViewById(R.id.textView4);
        tv4.setOnClickListener(new TextViewOnClickListener());
        tv5 = (TextView) v5.findViewById(R.id.textView5);
        tv5.setOnClickListener(new TextViewOnClickListener());
        tv6 = (TextView) v6.findViewById(R.id.textView6);
        tv6.setOnClickListener(new TextViewOnClickListener());
        tv7 = (TextView) v7.findViewById(R.id.textView1);
        tv7.setOnClickListener(new TextViewOnClickListener());

        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
    }

    private class TextViewOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {

            // TODO Auto-generated method stub
            Toast.makeText(DM_VotedListActivity.this, ((TextView) v).getText(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {

            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {

            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {

            // TODO Auto-generated method stub
            ((ViewPager) arg0).removeView(pageViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {

            // TODO Auto-generated method stub
            ((ViewPager) arg0).addView(pageViews.get(arg1));
            return pageViews.get(arg1);
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

        @Override
        public void finishUpdate(View arg0) {

            // TODO Auto-generated method stub

        }
    }

    private class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {

            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);

                if (arg0 != i) {
                    imageViews[i].setBackgroundResource(R.drawable.page_indicator);
                }
            }
        }
    }
}
