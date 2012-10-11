
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.HeadlineAdapter;
import com.wh.dm.widget.HorizontalPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DM_HeadLineActivity extends Activity {

    // test data
    String[] dataTitle = new String[] {
            "日首相承认低谷中国反制措施", "日本多家中国餐馆与企业被砸", "日记者：日本人希望中国更暴力", "美驻华大使馆座驾遭反日者围堵", "香港男子袭击2名日本人被捕",
            "日媒称中国2护卫舰进入钓鱼岛", "知名面粉企业产品含致癌物蹦砂", "媒体称王立军若不叛逃难揭真相", "美华裔工程师为中国公司窃机密",
            "湄公河惨案主犯糯康当庭翻供", "深圳“奶粉水客”走私千瓶饮料", "武汉坠梯4受害家庭仅剩5孤儿", "安徽怀宁拟27亿建爱情之都", "莱温斯基报复克林顿：他有性瘾",
            "最贪镇长挪1.78亿被判死缓",
    };

    String[] dataBody = new String[] {
            "称中日摩擦超出预想，考虑派特使访华修复关系。", "40分钟内连发3起袭击事件，现场可见大石块。", "抗议者越丧失理性，只会越多地伤害中国形象。",
            "骆家辉称未感到危险，北京治安有序，警方行动迅速。", "日要求港府确保人员安全，提醒在港日本人谨慎言行。", "中国巡逻船达15艘；日自卫队飞机战舰向钓鱼岛移动。",
            "产品销量全国第一；成人服用15克蹦砂可致死。", "“打黑英雄”不堪入目，官再大的人胡来也要遭报应。", "杨春来被控盗取源代码，面临10年监禁和25万美元罚款。",
            "受害者家属索赔数千万，求判糯康死刑。", "香港警方拘捕百余名内地水客，严打行动将持续一段时间。", "19个青壮年生命瞬间陨落，只留下老小和87.9万抚恤金。",
            "怀宁县为《孔雀东南飞》发生地，规划已通过评审。", "回忆录将透露交往期间堕胎细节，空成克林顿梦魇。", "伪造机场高速等工程拆迁补偿书，骗去3800万补偿款。",
    };

    int[] imgId = new int[] {
            R.drawable.test1_1, R.drawable.test1_2, R.drawable.test1_3, R.drawable.test1_4,
            R.drawable.test1_5, R.drawable.test1_6, R.drawable.test1_7, R.drawable.test1_8,
            R.drawable.test1_9, R.drawable.test1_10, R.drawable.test1_11, R.drawable.test1_12,
            R.drawable.test1_13, R.drawable.test1_14, R.drawable.test1_15,
    };

    private String[] titles;
    private int currentItem = 0;
    private View headerView;
    private LayoutInflater mInfalater;
    private ListView listView;
    private View footer;
    private Button btnFoolter;

    private final int SHOW_NEXT = 0011;
    private final boolean isRun = true;

    private HorizontalPager mPager;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_headline);
        init();
        thread.start();
    }

    private void init() {

        mInfalater = getLayoutInflater();
        listView = (ListView) findViewById(R.id.list);
        mRadioGroup = (RadioGroup) findViewById(R.id.tabs);
        mRadioGroup.setOnCheckedChangeListener(onCheckedChangedListener);
        mPager = (HorizontalPager) findViewById(R.id.horizontal_pager);
        mPager.setOnScreenSwitchListener(onScreenSwitchListener);
        mPager.setCurrentScreen(0, true);

        HeadlineAdapter adapter = new HeadlineAdapter(this);
        // add data
        TextView horizontalTitle = (TextView) findViewById(R.id.txt_horizontal_title);
        horizontalTitle.setText("湖北20余城管列队围观商户 用眼神执法");

        String title = getResources().getString(R.string.headline_title);
        String body = getResources().getString(R.string.headline_body);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.temp_news_img);
        for (int i = 0; i < dataTitle.length; i++) {
            title = dataTitle[i];
            body = dataBody[i];
            bmp = BitmapFactory.decodeResource(getResources(), imgId[i]);
            adapter.addItem(title, body, bmp);
        }

        // add footer for listview
        footer = mInfalater.inflate(R.layout.news_list_footer, null);
        btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
        listView.addFooterView(footer);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Intent intent = new Intent(DM_HeadLineActivity.this, DM_NewsDetailsActivity.class);
                startActivity(intent);
            }
        });

        btnFoolter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    private final RadioGroup.OnCheckedChangeListener onCheckedChangedListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup group, final int checkedId) {

            // Slide to the appropriate screen when the user checks a button.
            switch (checkedId) {
                case R.id.radio_btn_0:
                    mPager.setCurrentScreen(0, true);
                    break;
                case R.id.radio_btn_1:
                    mPager.setCurrentScreen(1, true);
                    break;
                case R.id.radio_btn_2:
                    mPager.setCurrentScreen(2, true);
                    break;
                case R.id.radio_btn_3:
                    mPager.setCurrentScreen(3, true);
                default:
                    break;
            }
        }
    };

    private final HorizontalPager.OnScreenSwitchListener onScreenSwitchListener = new HorizontalPager.OnScreenSwitchListener() {
        @Override
        public void onScreenSwitched(final int screen) {

            // Check the appropriate button when the user swipes screens.
            switch (screen) {
                case 0:
                    mRadioGroup.check(R.id.radio_btn_0);
                    break;
                case 1:
                    mRadioGroup.check(R.id.radio_btn_1);
                    break;
                case 2:
                    mRadioGroup.check(R.id.radio_btn_2);
                    break;
                case 3:
                    mRadioGroup.check(R.id.radio_btn_3);
                    break;
                default:
                    break;
            }
        }

    };

    private final Handler mflipperHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            switch (msg.what) {
                case SHOW_NEXT:
                    showNextView();
                    break;

                default:
                    break;
            }
        }

    };

    private final Thread thread = new Thread() {
        @Override
        public void run() {

            while (isRun) {
                try {
                    Thread.sleep(1000 * 4);
                    Message msg = new Message();
                    msg.what = SHOW_NEXT;
                    mflipperHandler.sendMessage(msg);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }
    };

    private void showNextView() {

        currentItem = mPager.getCurrentScreen();
        if (currentItem < 3) {
            mPager.setCurrentScreen(++currentItem, true);
        } else {
            currentItem = 0;
            mPager.setCurrentScreen(currentItem, true);
        }
    }

}
