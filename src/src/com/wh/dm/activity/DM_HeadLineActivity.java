
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
            "��������ϵ͹��й����ƴ�ʩ", "�ձ�����й��͹�����ҵ����", "�ռ��ߣ��ձ���ϣ���й�������", "��פ����ʹ�������ⷴ����Χ��", "�������Ϯ��2���ձ��˱���",
            "��ý���й�2������������㵺", "֪�������ҵ��Ʒ���°����ɰ", "ý������������������ѽ�����", "�����Ṥ��ʦΪ�й���˾�Ի���",
            "�ع��ӲҰ�����Ŵ����ͥ����", "���ڡ��̷�ˮ�͡���˽ǧƿ����", "�人׹��4�ܺ���ͥ��ʣ5�¶�", "���ջ�����27�ڽ�����֮��", "����˹���������ֶ٣��������",
            "��̰��Ų1.78�ڱ�������",
    };

    String[] dataBody = new String[] {
            "������Ħ������Ԥ�룬��������ʹ�û��޸���ϵ��", "40����������3��Ϯ���¼����ֳ��ɼ���ʯ�顣", "������Խɥʧ���ԣ�ֻ��Խ����˺��й�����",
            "��һԳ�δ�е�Σ�գ������ΰ����򣬾����ж�Ѹ�١�", "��Ҫ��۸�ȷ����Ա��ȫ�������ڸ��ձ��˽������С�", "�й�Ѳ�ߴ���15�ң��������ӷɻ�ս������㵺�ƶ���",
            "��Ʒ����ȫ����һ�����˷���15�˱�ɰ��������", "�����Ӣ�ۡ�������Ŀ�����ٴ���˺���ҲҪ�ⱨӦ��", "������ص�ȡԴ���룬����10������25����Ԫ���",
            "�ܺ��߼���������ǧ������Ŵ�����̡�", "��۾����в��������ڵ�ˮ�ͣ��ϴ��ж�������һ��ʱ�䡣", "19����׳������˲�����䣬ֻ������С��87.9������",
            "������Ϊ����ȸ���Ϸɡ������أ��滮��ͨ������", "����¼��͸¶�����ڼ��̥ϸ�ڣ��ճɿ��ֶ����ʡ�", "α��������ٵȹ��̲�Ǩ�����飬ƭȥ3800�򲹳��",
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
        horizontalTitle.setText("����20��ǹ��ж�Χ���̻� ������ִ��");

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
