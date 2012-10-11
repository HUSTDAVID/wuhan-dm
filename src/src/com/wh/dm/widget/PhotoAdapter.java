
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

    public void addItem(Bitmap leftBmp, String leftTitle, int leftReview, int leftNum,
            Bitmap rightBmp, String rightTitle, int rightReview, int rightNum) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        // add left data
        map.put("leftImage", leftBmp);
        map.put("leftTitle", leftTitle);
        map.put("leftReview", leftReview);
        map.put("leftNum", leftNum);
        // add rigth data
        map.put("rightImage", rightBmp);
        map.put("rightTitle", rightTitle);
        map.put("rightReview", rightReview);
        map.put("rightNum", rightNum);
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

            holder.imgLeft = (ImageView) convertView.findViewById(R.id.img_left_potos_item);
            holder.txtLeftTitle = (TextView) convertView.findViewById(R.id.txt_left_photos_title);
            holder.txtLeftReview = (TextView) convertView.findViewById(R.id.txt_left_review_total);
            holder.txtLeftNum = (TextView) convertView.findViewById(R.id.txt_left_num_total);

            holder.imgRight = (ImageView) convertView.findViewById(R.id.img_right_potos_item);
            holder.txtRightTitle = (TextView) convertView.findViewById(R.id.txt_right_photos_title);
            holder.txtRightReview = (TextView) convertView
                    .findViewById(R.id.txt_right_review_total);
            holder.txtRightNum = (TextView) convertView.findViewById(R.id.txt_right_num_total);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imgLeft.setImageBitmap((Bitmap) mData.get(position).get("leftImage"));
        holder.txtLeftTitle.setText(mData.get(position).get("leftTitle").toString());
        holder.txtLeftReview.setText(mData.get(position).get("leftReview").toString());
        holder.txtLeftNum.setText(mData.get(position).get("leftNum").toString());

        holder.imgRight.setImageBitmap((Bitmap) mData.get(position).get("rightImage"));
        holder.txtRightTitle.setText(mData.get(position).get("rightTitle").toString());
        holder.txtRightReview.setText(mData.get(position).get("rightReview").toString());
        holder.txtRightNum.setText(mData.get(position).get("rightNum").toString());

        return convertView;
    }

    static class ViewHolder {
        ImageView imgLeft;
        TextView txtLeftTitle;
        TextView txtLeftReview;
        TextView txtLeftNum;

        ImageView imgRight;
        TextView txtRightTitle;
        TextView txtRightReview;
        TextView txtRightNum;

    }

}
