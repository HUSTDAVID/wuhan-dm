
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsReplyAdapter extends BaseAdapter {

    List<Map<String, Object>> mData;
    LayoutInflater mInfalater;
    Context context;

    public NewsReplyAdapter(Context context) {

        this.context = context;
        mInfalater = LayoutInflater.from(context);
        mData = new ArrayList<Map<String, Object>>();
    }

    public void addItem(String name, String time, String body, String top) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("time", time);
        map.put("body", body);
        map.put("top", top);
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
            convertView = mInfalater.inflate(R.layout.news_reply_item, null);
            holder.txtBody = (TextView) convertView.findViewById(R.id.txt_replay_body);
            holder.txtTime = (TextView) convertView.findViewById(R.id.txt_reply_time);
            holder.txtName = (TextView) convertView.findViewById(R.id.txt_reply_name);
            holder.btnReply = (Button) convertView.findViewById(R.id.btn_reply);
            holder.btnTop = (Button) convertView.findViewById(R.id.btn_reply_top);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtBody.setText(mData.get(position).get("body").toString());
        holder.txtName.setText(mData.get(position).get("name").toString());
        holder.txtTime.setText(mData.get(position).get("time").toString());
        holder.btnTop.setText(mData.get(position).get("top").toString());

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtTime;
        TextView txtBody;
        Button btnTop;
        Button btnReply;
    }

}
