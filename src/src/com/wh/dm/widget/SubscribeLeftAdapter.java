
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscribeLeftAdapter extends BaseAdapter {

    List<Map<String, Object>> mData;
    LayoutInflater mInflater;
    Context context;

    public SubscribeLeftAdapter(Context context) {

        this.context = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<Map<String, Object>>();
    }

    public void addItem(String title, Bitmap bmp, int id) {

        Map map = new HashMap<String, Object>();
        map.put("title", title);
        map.put("image", bmp);
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
            convertView = mInflater.inflate(R.layout.dm_sub_left_item, null);
            holder.imgLeft = (ImageView) convertView.findViewById(R.id.img_sub_left_item);
            holder.imgRight = (ImageView) convertView.findViewById(R.id.img_sub_right_item);
            holder.txt = (TextView) convertView.findViewById(R.id.txt_sub_left_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.sub_right_bg));
        }

        holder.imgLeft.setImageBitmap((Bitmap) mData.get(position).get("image"));
        holder.txt.setText(mData.get(position).get("title").toString());

        return convertView;
    }

    class ViewHolder {
        ImageView imgLeft;
        ImageView imgRight;
        TextView txt;
    }

}
