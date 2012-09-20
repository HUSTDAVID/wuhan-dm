
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DM_VotedListActivity extends Activity {
    private ViewPager viewPager;
    private ArrayList<View> pageViews;
    private View dot;
    private View[] dots;

    private ViewGroup main;

    private ViewGroup group;

    Button btnVote;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.dm_voteitem1, null);
        View v2 = inflater.inflate(R.layout.dm_voteitem1, null);
        View v3 = inflater.inflate(R.layout.dm_voteitem1, null);
        View v4 = inflater.inflate(R.layout.dm_voteitem1, null);
        View v5 = inflater.inflate(R.layout.dm_voteitem1, null);
        View v6 = inflater.inflate(R.layout.dm_voteitem1, null);
        View v7 = inflater.inflate(R.layout.dm_voteitem1, null);

        btnVote = (Button) v1.findViewById(R.id.btn_vote);
        btnVote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DM_VotedListActivity.this, DM_Vote2Activity.class);
                startActivity(intent);
            }
        });

        pageViews = new ArrayList<View>();
        pageViews.add(v1);
        pageViews.add(v2);
        pageViews.add(v3);
        pageViews.add(v4);
        pageViews.add(v5);
        pageViews.add(v6);
        pageViews.add(v7);

        dots = new View[pageViews.size()];
        main = (ViewGroup) inflater.inflate(R.layout.activity_votemain, null);

        group = (ViewGroup) main.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) main.findViewById(R.id.guidePages);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(12, 12);
        params.setMargins(0, 0, 20, 10);
        for (int i = 0; i < pageViews.size(); i++) {
            dot = new ImageView(DM_VotedListActivity.this);
            dot.setLayoutParams(new LayoutParams(12, 12));
            dot.setLayoutParams(params);
            dots[i] = dot;

            if (i == 0) {

                dots[i].setBackgroundResource(R.drawable.dot_in_vote_focused);
            } else {
                dots[i].setBackgroundResource(R.drawable.dot_in_vote_normal);
            }

            group.addView(dots[i]);
        }

        setContentView(main);

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

            for (int i = 0; i < dots.length; i++) {
                dots[arg0].setBackgroundResource(R.drawable.dot_in_vote_focused);

                if (arg0 != i) {
                    dots[i].setBackgroundResource(R.drawable.dot_in_vote_normal);
                }
            }
        }
    }
}
