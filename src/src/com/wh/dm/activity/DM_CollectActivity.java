
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.CollectAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DM_CollectActivity extends Activity {

    private ListView collect_list;
    List<Map<String, Object>> list;
    List<Map<String, Object>> list1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_collect);
        collect_list = (ListView) findViewById(R.id.list);

        list = new ArrayList<Map<String, Object>>();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("txt_collect_title", "为何美国人拿的奥运奖牌比我们多");
            map.put("txt_collect_body", "收藏时间：2012-12-30  16:12");
            map.put("check", false);
            list.add(map);
        }
        CollectAdapter adapter = new CollectAdapter(this, list);
        collect_list.setAdapter(adapter);

    }

}
