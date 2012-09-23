
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MzineLongAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<Map<String, Object>> mData;

    public MzineLongAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<Map<String, Object>>();
    }

    public void addItem(Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4, Bitmap bmp5,
            Bitmap bmp6, Bitmap bmp7) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("img1", bmp1);
        map.put("img2", bmp2);
        map.put("img3", bmp3);
        map.put("img4", bmp4);
        map.put("img5", bmp5);
        map.put("img6", bmp6);
        map.put("img7", bmp7);

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
            convertView = mInflater.inflate(R.layout.dm_mzine_long_item, null);
            holder.img1 = (ImageView) convertView.findViewById(R.id.img_mzine_long_1);
            holder.img2 = (ImageView) convertView.findViewById(R.id.img_mzine_long_2);
            holder.img3 = (ImageView) convertView.findViewById(R.id.img_mzine_long_3);
            holder.img4 = (ImageView) convertView.findViewById(R.id.img_mzine_long_4);
            holder.img5 = (ImageView) convertView.findViewById(R.id.img_mzine_long_5);
            holder.img6 = (ImageView) convertView.findViewById(R.id.img_mzine_long_6);
            holder.img7 = (ImageView) convertView.findViewById(R.id.img_mzine_long_7);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        holder.img1.setImageBitmap((Bitmap) mData.get(position).get("img1"));
        holder.img2.setImageBitmap((Bitmap) mData.get(position).get("img2"));
        holder.img3.setImageBitmap((Bitmap) mData.get(position).get("img3"));
        holder.img4.setImageBitmap((Bitmap) mData.get(position).get("img4"));
        holder.img5.setImageBitmap((Bitmap) mData.get(position).get("img5"));
        holder.img6.setImageBitmap((Bitmap) mData.get(position).get("img6"));
        holder.img7.setImageBitmap((Bitmap) mData.get(position).get("img7"));

        return convertView;
    }

    public static class ViewHolder {
        ImageView img1;
        ImageView img2;
        ImageView img3;
        ImageView img4;
        ImageView img5;
        ImageView img6;
        ImageView img7;

    }

}
