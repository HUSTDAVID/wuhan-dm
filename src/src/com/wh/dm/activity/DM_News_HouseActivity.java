
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
            "机构预测今年房价将下调5%", "买房故事：一套房两个人的青春", "淘房好时节刚需三步三选巧置业", "购房时不得不知的房地产名词", "购精装房装修合同约定要细致",
            "多地数据系统建成为房产税清障", "京城小产权别墅变身旅游农庄", "北京国土局对小产权房分类处理", "库存危机真相：存量房等待入市",
            "销售放缓楼市库存消化周期拉长", "时评：楼市调控任务依然艰巨", "9月11家房企开融资闸门", "20余家房企斥巨资逾70亿拿地", "密集拿地逻辑：地价回到08年",
            "河南某镇3000多万建办公楼",
    };

    String[] dataBody = new String[] {
            "机构称楼市改善，并将今年房价降幅预测由10%调至5%。", "即时市场再低迷，照样得有一部分人是刚性需求购买者。",
            "80后步入谈婚论嫁年龄，结婚购房成为不可回避的问题。", "购房时，在商品房广告中常见的名词也要特别关注。", "精装房交楼时往往货不对板，此时购房者想维权却不易。",
            "广州等地已基本完成存量房统计，为房产税十点清障。", "京城一在售小产权别墅项目，以旅游农庄形式变身入市。", "“北京国土局相关负责人表示，处理小产权房不会一刀切。",
            "在金九失色的趋势下，开发商去库存压力仍然很大。", "热点城市楼市库存已呈微增态势，库存消化周期将拉长。", "房价似乎在降，但离大多数人的期望依然还很遥远。",
            "拿地凶猛、融资开闸，下半年楼市复苏后房企重新活跃。", "9月以来，上市房企掀起了一波拿地狂潮，斥资超70亿。", "万科总裁称此事拿地并非下注后市回暖，而是低价补仓。",
            "河南信阳市明港镇斥资3000多万建办公楼，严重超标。",
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
