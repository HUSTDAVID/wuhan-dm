
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.activity.NewsMoreReplyActivity;
import com.wh.dm.type.Comment;
import com.wh.dm.util.TimeUtil;

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
    public Handler handler;

    public void setList(ArrayList<Comment> comments) {

        if (mData == null) {
            mData = new ArrayList<Map<String, Object>>();
        }
        for (int i = 0; i < comments.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", context.getString(R.string.review_name));
            map.put("time", comments.get(i).getDtime());
            map.put("body", comments.get(i).getMsg());
            map.put("top", comments.get(i).getGood());
            map.put("fid", comments.get(i).getId());
            mData.add(map);
        }
        notifyDataSetChanged();
    }

    public NewsReplyMoreAdapter(Context context) {

        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void addItem(String name, String time, String body, String top,
            NewsReplyFloorAdapter floorAdapter, int fid) {

        if (mData == null) {
            mData = new ArrayList<Map<String, Object>>();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("time", time);
        map.put("body", body);
        map.put("top", top);
        map.put("fid", fid);
        map.put("floor_adapter", floorAdapter);

        mData.add(map);
        notifyDataSetChanged();
        // notifyDataSetInvalidated();
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
        holder.txtTime.setText(TimeUtil.getTimeInterval(mData.get(position).get("time").toString(),
                context));
        holder.btnTop.setText(context.getString(R.string.top)
                + mData.get(position).get("top").toString());
        holder.btnTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Message message = new Message();
                message.what = NewsMoreReplyActivity.MSG_PUSH_TOP;
                Bundle bundle = new Bundle();
                bundle.putString("fid", "" + mData.get(position).get("fid"));
                message.setData(bundle);
                handler.sendMessage(message);

            }

        });
        holder.btnReply.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Message message = new Message();
                message.what = NewsMoreReplyActivity.MSG_REPLY;
                Bundle bundle = new Bundle();
                bundle.putString("fid", "" + mData.get(position).get("fid"));
                bundle.putBoolean("isReply", false);
                message.setData(bundle);
                handler.sendMessage(message);

            }

        });
        NewsReplyFloorAdapter adapter = (NewsReplyFloorAdapter) (mData.get(position)
                .get("floor_adapter"));
        if (adapter != null && adapter.getCount() > 0) {
            holder.layout.setVisibility(View.VISIBLE);
            holder.lvFloor.setAdapter(adapter);
            setListViewHeightBasedOnChildren(holder.lvFloor);
        } else {
            holder.layout.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void setHandler(Handler _handler) {

        handler = _handler;

    }

    public static class ViewHolder {
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
