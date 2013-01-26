
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.activity.NewsDetailsActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
    LayoutInflater mInflater;
    Context context;
    public Handler handler;

    public NewsReplyAdapter(Context context) {

        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void clearItem() {

        if (mData != null)
            mData.clear();
    }

    public void addItem(String name, String time, String body, String top, int fid) {

        if (mData == null) {
            mData = new ArrayList<Map<String, Object>>();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        // map.put("name", context.getString(R.string.review_name) + name);
        map.put("name", context.getString(R.string.review_name));
        map.put("time", time);
        map.put("body", body);
        map.put("top", top);
        map.put("fid", fid);
        mData.add(map);
        notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {

        if (mData == null) {
            return 0;
        } else {
            return mData.size();
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.news_reply_item_white, null);
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
        holder.btnTop.setText(context.getString(R.string.top)
                + mData.get(position).get("top").toString());

        holder.btnTop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = NewsDetailsActivity.MSG_PUSH_TOP;
                Bundle bundle = new Bundle();
                bundle.putString("fid", "" + mData.get(position).get("fid"));
                message.setData(bundle);
                handler.sendMessage(message);
            }

        });

        holder.btnReply.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = NewsDetailsActivity.MSG_REPLY;
                Bundle bundle = new Bundle();
                bundle.putString("fid", "" + mData.get(position).get("fid"));
                bundle.putBoolean("isReply", false);
                message.setData(bundle);
                handler.sendMessage(message);
            }

        });
        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtTime;
        TextView txtBody;
        Button btnTop;
        Button btnReply;
    }

    public void setHandler(Handler _handler) {

        handler = _handler;
    }
}
