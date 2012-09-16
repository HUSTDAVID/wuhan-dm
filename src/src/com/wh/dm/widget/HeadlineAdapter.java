
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

public class HeadlineAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public HeadlineAdapter(Context context) {

        mData = new ArrayList<Map<String, Object>>();
        mInflater = LayoutInflater.from(context);

    }

    public void addItem(String title, String body, Bitmap bmp) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", title);
        map.put("image", bmp);
        map.put("body", body);
        mData.add(map);
        notifyDataSetChanged();
    }

    public int getCount() {

        return mData.size();
    }

    public Object getItem(int position) {

        return mData.get(position);
    }

    public long getItemId(int position) {

        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.headline_item, null);
            holder.txt_title = (TextView) view.findViewById(R.id.txt_headline_item_title);
            holder.txt_body = (TextView) view.findViewById(R.id.txt_headline_item_body);
            holder.img = (ImageView) view.findViewById(R.id.img_headline_item);
            view.setTag(holder);
        } else

        {
            holder = (ViewHolder) view.getTag();
        }

        holder.txt_title.setText(mData.get(position).get("title").toString());
        holder.txt_body.setText(mData.get(position).get("body").toString());
        holder.img.setImageBitmap((Bitmap) mData.get(position).get("image"));

        return view;
    }

    static class ViewHolder {
        public TextView txt_title;

        public TextView txt_body;
        public ImageView img;
    }

}
