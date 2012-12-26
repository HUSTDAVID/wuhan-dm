
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.preference.Preferences;
import com.wh.dm.type.Vote;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.TimeUtil;
import com.wh.dm.widget.GetVoteView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VotedListActivity extends Activity {
    private ViewPager viewPager;
    private ArrayList<View> pageViews;
    private View dot;
    private View[] dots;
    private ViewGroup main;
    private ViewGroup group;
    private Button btnVote;
    private LinearLayout loadLayout;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private ArrayList<Vote> votes;
    private final int MSG_GET_VOTES = 1;
    private GetVotesTask getVotesTask = null;
    private int currentSelelct = 0;
    private int sid;
    private LayoutInflater inflate;

    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MSG_GET_VOTES) {
                if (getVotesTask != null) {
                    getVotesTask.cancel(true);
                    getVotesTask = null;
                }
                getVotesTask = new GetVotesTask();
                getVotesTask.execute();

            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        sid = getIntent().getIntExtra("sid", 6);
        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        handler.sendEmptyMessage(MSG_GET_VOTES);

        inflate = getLayoutInflater();
        pageViews = new ArrayList<View>();
        main = (ViewGroup) inflate.inflate(R.layout.activity_votemain, null);
        loadLayout = (LinearLayout) main.findViewById(R.id.vote_load);
        loadLayout.setVisibility(View.VISIBLE);
        group = (ViewGroup) main.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) main.findViewById(R.id.guidePages);
        View v1 = inflate.inflate(R.layout.dm_voteitem1, null);
        pageViews.add(v1);
        setContentView(main);
        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());

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

    private class TextViewOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {

            // TODO Auto-generated method stub
            Toast.makeText(VotedListActivity.this, ((TextView) v).getText(), Toast.LENGTH_LONG)
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

            currentSelelct = arg0;
            for (int i = 0; i < dots.length; i++) {
                dots[arg0].setBackgroundResource(R.drawable.dot_in_vote_focused);

                if (arg0 != i) {
                    dots[i].setBackgroundResource(R.drawable.dot_in_vote_normal);
                }
            }
        }
    }

    class GetVotesTask extends AsyncTask<Void, Void, ArrayList<Vote>> {

        Exception reason;
        {
        }

        @Override
        protected ArrayList<Vote> doInBackground(Void... params) {

            try {
                votes = wh_dmApi.getVote(sid, Preferences.getMachineId(VotedListActivity.this));
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
            }
            return votes;
        }

        @Override
        protected void onPostExecute(ArrayList<Vote> result) {

            if (result != null && result.size() > 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(12, 12);
                params.setMargins(0, 0, 20, 10);
                dots = new View[result.size()];
                pageViews.clear();
                for (int i = 0; i < result.size(); i++) {
                    View view = GetVoteView.getView(result.get(i), VotedListActivity.this);

                    ImageButton btnBack = (ImageButton) view.findViewById(R.id.img_header3_back);
                    btnBack.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            finish();
                        }
                    });

                    TextView txtInfo = (TextView) view.findViewById(R.id.vote_ing_3);
                    Button btnVote = (Button) view.findViewById(R.id.btn_vote);
                    if (result.get(i).getRcount() == 0
                            && !TimeUtil.isPass(result.get(i).getEndtime())) {
                        btnVote.setText(getString(R.string.vote));
                        txtInfo.setText(getString(R.string.voting));
                        btnVote.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(VotedListActivity.this,
                                        Vote2Activity.class);
                                intent.putExtra("aid", votes.get(currentSelelct).getAid());
                                intent.putExtra("enable", true);
                                intent.putExtra("name", votes.get(currentSelelct).getVotename());
                                intent.putExtra("votenote", votes.get(currentSelelct).getVotenote());
                                startActivity(intent);

                            }
                        });
                    } else {
                        if (TimeUtil.isPass(result.get(i).getEndtime())) {
                            txtInfo.setText(getString(R.string.voted));
                        } else {
                            txtInfo.setText(getString(R.string.take_part));
                        }
                        btnVote.setText(getString(R.string.watch_result));
                        btnVote.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(VotedListActivity.this,
                                        Vote2Activity.class);
                                intent.putExtra("aid", votes.get(currentSelelct).getAid());
                                intent.putExtra("enable", false);
                                intent.putExtra("name", votes.get(currentSelelct).getVotename());
                                intent.putExtra("votenote", votes.get(currentSelelct).getVotenote());
                                startActivity(intent);

                            }
                        });
                    }

                    pageViews.add(view);
                    dot = new ImageView(VotedListActivity.this);
                    dot.setLayoutParams(new LayoutParams(12, 12));
                    dot.setLayoutParams(params);
                    dots[i] = dot;
                    if (i == 0) {

                        dots[i].setBackgroundResource(R.drawable.dot_in_vote_focused);
                    } else {
                        dots[i].setBackgroundResource(R.drawable.dot_in_vote_normal);
                    }
                    group.addView(dots[i]);
                    viewPager.setAdapter(new GuidePageAdapter());
                    viewPager.setOnPageChangeListener(new GuidePageChangeListener());
                }
            } else {
                if (wh_dmApp.isConnected()) {
                    NotificationUtil.showShortToast(getResources().getString(R.string.badconnect),
                            VotedListActivity.this);
                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            VotedListActivity.this);
                }

            }
            loadLayout.setVisibility(View.GONE);
            super.onPostExecute(result);
        }
    }

}
