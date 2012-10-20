
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsReplyFloorAdapter extends BaseAdapter {

    List<Map<String, Object>> mData;
    LayoutInflater mInfalater;
    Context context;

    public NewsReplyFloorAdapter(Context context) {

        this.context = context;
        mInfalater = LayoutInflater.from(context);
        mData = new ArrayList<Map<String, Object>>();
    }

    public void addItem(String name, String text, int floor) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("text", text);
        map.put("floor", floor);

        mData.add(map);
        notifyDataSetChanged();
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
            convertView = mInfalater.inflate(R.layout.news_reply_floor_item, null);
            holder.txtFloor = (TextView) convertView.findViewById(R.id.txt_floor);
            holder.txtName = (TextView) convertView.findViewById(R.id.txt_floor_name);
            holder.txtText = (TextView) convertView.findViewById(R.id.txt_floor_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtFloor.setText(mData.get(position).get("floor").toString());
        holder.txtName.setText(mData.get(position).get("name").toString());
        holder.txtText.setText(mData.get(position).get("text").toString());

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtText;
        TextView txtFloor;
    }

}
