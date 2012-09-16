
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubscribeAdapter extends BaseAdapter {

    ArrayList<Map<String, Object>> mData;
    LayoutInflater mInflater;
    Context context;

    public SubscribeAdapter(Context context) {

        this.context = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<Map<String, Object>>();
    }

    public void addItem(Bitmap bmp, String title, boolean isSub) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("image", bmp);
        map.put("title", title);
        map.put("isSub", isSub);
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
    public View getView(int position, View conterView, ViewGroup parent) {

        ViewHolder holder = null;
        if (conterView == null) {
            conterView = mInflater.inflate(R.layout.sub_item, null);
            holder = new ViewHolder();
            holder.img = (ImageView) conterView.findViewById(R.id.img_sub);
            holder.txt = (TextView) conterView.findViewById(R.id.txt_sub_title);
            holder.btn = (Button) conterView.findViewById(R.id.btn_sub);
            conterView.setTag(holder);
        } else {
            holder = (ViewHolder) conterView.getTag();
        }

        holder.img.setImageBitmap((Bitmap) mData.get(position).get("image"));
        holder.txt.setText(mData.get(position).get("title").toString());
        boolean isSub = (Boolean) mData.get(position).get("isSub");
        if (isSub) {
            holder.btn.setBackgroundResource(R.drawable.btn_sub_have);
            holder.btn.setText(context.getResources().getString(R.string.sub_have));
            holder.btn.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.btn.setBackgroundResource(R.drawable.btn_sub_no);
            holder.btn.setText(context.getResources().getString(R.string.sub_no));
            holder.btn.setTextColor(context.getResources().getColor(R.color.white));
        }

        return conterView;
    }

    static class ViewHolder {

        ImageView img;
        TextView txt;
        Button btn;
    }

}
