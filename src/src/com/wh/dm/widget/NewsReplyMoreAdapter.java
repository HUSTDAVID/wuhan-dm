
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsReplyMoreAdapter extends BaseAdapter {

    List<Map<String, Object>> mData;
    LayoutInflater mInflater;
    Context context;

    public NewsReplyMoreAdapter(Context context) {

        this.context = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<Map<String, Object>>();
    }

    public void addItem(String name, String time, String body, String top,
            NewsReplyFloorAdapter floorAdapter) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("time", time);
        map.put("body", body);
        map.put("top", top);
        map.put("floor_adapter", floorAdapter);

        mData.add(map);
        notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {

        return mData.size();
    }

    @Override
    public Object getItem(int location) {

        return mData.get(location);
    }

    @Override
    public long getItemId(int location) {

        return location;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.news_reply_item, null);
            holder.txtBody = (TextView) convertView.findViewById(R.id.txt_replay_body);
            holder.txtTime = (TextView) convertView.findViewById(R.id.txt_reply_time);
            holder.txtName = (TextView) convertView.findViewById(R.id.txt_reply_name);
            holder.btnReply = (Button) convertView.findViewById(R.id.btn_reply);
            holder.btnTop = (Button) convertView.findViewById(R.id.btn_reply_top);
            holder.lvFloor = (ListView) convertView.findViewById(R.id.lv_news_floor);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout_for_lv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtBody.setText(mData.get(position).get("body").toString());
        holder.txtName.setText(mData.get(position).get("name").toString());
        holder.txtTime.setText(mData.get(position).get("time").toString());
        holder.btnTop.setText(mData.get(position).get("top").toString());

        NewsReplyFloorAdapter adapter = (NewsReplyFloorAdapter) (mData.get(position)
                .get("floor_adapter"));
        if (adapter != null && adapter.getCount() > 0) {
            holder.layout.setVisibility(View.VISIBLE);
            holder.lvFloor.setAdapter(adapter);
            setListViewHeightBasedOnChildren(holder.lvFloor);
        }

        return convertView;
    }

    static class ViewHolder {
        LinearLayout layout;
        TextView txtName;
        TextView txtTime;
        TextView txtBody;
        Button btnTop;
        Button btnReply;
        ListView lvFloor;
    }

    // a method reset listview hight
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
