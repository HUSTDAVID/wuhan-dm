
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscibeRightAdapter extends BaseAdapter {

    List<Map<String, Object>> mData;
    LayoutInflater mInflater;

    public SubscibeRightAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<Map<String, Object>>();
    }

    public void addItem(String title, boolean isSelecet, int id) {

        Map map = new HashMap<String, Object>();
        map.put("title", title);
        map.put("isSelecet", isSelecet);
        map.put("id", id);
        mData.add(map);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return mData.size();
    }

    @Override
    public Object getItem(int position) {

        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.dm_sub_right_item, null);
            holder.txt_title = (TextView) convertView.findViewById(R.id.txt_sub_right);
            holder.cbox = (CheckBox) convertView.findViewById(R.id.cbox_sub_right);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_title.setText(mData.get(position).get("title").toString());
        boolean isSelecet = (Boolean) mData.get(position).get("isSelecet");
        if (isSelecet) {
            holder.cbox.setChecked(true);
        } else {
            holder.cbox.setChecked(false);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView txt_title;
        CheckBox cbox;
    }
}
