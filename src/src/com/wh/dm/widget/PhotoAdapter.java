
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
import java.util.Map;

public class PhotoAdapter extends BaseAdapter {

    ArrayList<Map<String, Object>> mData;
    LayoutInflater mInflater;

    public PhotoAdapter(Context context) {

        mData = new ArrayList<Map<String, Object>>();
        mInflater = LayoutInflater.from(context);
    }

    public void addItem(Bitmap bmp, String title, int review, int num) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("image", bmp);
        map.put("title", title);
        map.put("review", review);
        map.put("num", num);
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
            convertView = mInflater.inflate(R.layout.photos_item, null);

            holder.img = (ImageView) convertView.findViewById(R.id.img_potos_item);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txt_photos_title);
            holder.txtReview = (TextView) convertView.findViewById(R.id.txt_review_total);
            holder.txtNum = (TextView) convertView.findViewById(R.id.txt_num_total);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.img.setImageBitmap((Bitmap) mData.get(position).get("image"));
        holder.txtTitle.setText(mData.get(position).get("title").toString());
        holder.txtReview.setText(mData.get(position).get("review").toString());
        holder.txtNum.setText(mData.get(position).get("num").toString());

        return convertView;
    }

    static class ViewHolder {
        ImageView img;
        TextView txtTitle;
        TextView txtReview;
        TextView txtNum;

    }

}
