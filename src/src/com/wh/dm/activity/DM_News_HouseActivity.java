
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.HeadlineAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DM_News_HouseActivity extends Activity {

    private ListView lv;

    String[] dataTitle = new String[] {
            "����Ԥ����귿�۽��µ�5%", "�򷿹��£�һ�׷������˵��ഺ", "�Է���ʱ�ڸ���������ѡ����ҵ", "����ʱ���ò�֪�ķ��ز�����", "����װ��װ�޺�ͬԼ��Ҫϸ��",
            "�������ϵͳ����Ϊ����˰����", "����С��Ȩ������������ũׯ", "���������ֶ�С��Ȩ�����ദ��", "���Σ�����ࣺ�������ȴ�����",
            "���۷Ż�¥�п��������������", "ʱ����¥�е���������Ȼ���", "9��11�ҷ�������բ��", "20��ҷ���������70���õ�", "�ܼ��õ��߼����ؼۻص�08��",
            "����ĳ��3000���򽨰칫¥",
    };

    String[] dataBody = new String[] {
            "������¥�и��ƣ��������귿�۽���Ԥ����10%����5%��", "��ʱ�г��ٵ��ԣ���������һ�������Ǹ����������ߡ�",
            "80����̸���ۼ����䣬��鹺����Ϊ���ɻرܵ����⡣", "����ʱ������Ʒ������г���������ҲҪ�ر��ע��", "��װ����¥ʱ���������԰壬��ʱ��������άȨȴ���ס�",
            "���ݵȵ��ѻ�����ɴ�����ͳ�ƣ�Ϊ����˰ʮ�����ϡ�", "����һ����С��Ȩ������Ŀ��������ũׯ��ʽ�������С�", "��������������ظ����˱�ʾ������С��Ȩ������һ���С�",
            "�ڽ��ʧɫ�������£�������ȥ���ѹ����Ȼ�ܴ�", "�ȵ����¥�п���ѳ�΢��̬�ƣ�����������ڽ�������", "�����ƺ��ڽ������������˵�������Ȼ����ңԶ��",
            "�õ����͡����ʿ�բ���°���¥�и��պ������»�Ծ��", "9�����������з���������һ���õؿ񳱣����ʳ�70�ڡ�", "����ܲóƴ����õز�����ע���л�ů�����ǵͼ۲��֡�",
            "�������������������3000���򽨰칫¥�����س��ꡣ",
    };

    int[] imgId = new int[] {
            R.drawable.test2_1, R.drawable.test2_2, R.drawable.test2_3, R.drawable.test2_4,
            R.drawable.test2_5, R.drawable.test2_6, R.drawable.test2_7, R.drawable.test2_8,
            R.drawable.test2_9, R.drawable.test2_10, R.drawable.test2_11, R.drawable.test2_12,
            R.drawable.test2_13, R.drawable.test2_14, R.drawable.test2_15,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_house);
      /*  lv = (ListView) findViewById(R.id.news_list_house);
        HeadlineAdapter adapter = new HeadlineAdapter(this);
        // add data
        String title = getResources().getString(R.string.headline_title);
        String body = getResources().getString(R.string.headline_body);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.temp_news_img);
        for (int i = 0; i < dataTitle.length; i++) {
            title = dataTitle[i];
            body = dataBody[i];
            bmp = BitmapFactory.decodeResource(getResources(), imgId[i]);
            adapter.addItem(title, body, bmp);
        }
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Intent intent = new Intent(DM_News_HouseActivity.this, DM_NewsDetailsActivity.class);
                startActivity(intent);
            }
        });*/
    }
}
