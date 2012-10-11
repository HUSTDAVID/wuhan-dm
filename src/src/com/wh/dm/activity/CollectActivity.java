
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.CollectAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectActivity extends Activity {

    private ListView collect_list;
    private ImageButton btnBack;
    List<Map<String, Object>> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_collect);
        collect_list = (ListView) findViewById(R.id.list);
        btnBack = (ImageButton) findViewById(R.id.BackButton);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

        list = new ArrayList<Map<String, Object>>();
        LayoutInflater inflater = LayoutInflater.from(this);

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("txt_collect_title", "银行多给储户400元 冻结其账户逼“还钱”");
        map1.put("txt_collect_body", "收藏时间：2012-12-30  16:12");
        map1.put("check", false);
        list.add(map1);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("txt_collect_title", "为何美国人拿的奥运奖牌比我们多");
        map2.put("txt_collect_body", "收藏时间：2012-12-30  16:12");
        map2.put("check", false);
        list.add(map2);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("txt_collect_title", "闽南制造业被高税费拖垮 大批工厂停厂出租");
        map3.put("txt_collect_body", "收藏时间：2012-12-30  16:12");
        map3.put("check", false);
        list.add(map3);

        CollectAdapter adapter = new CollectAdapter(this, list);
        collect_list.setAdapter(adapter);

    }
}
