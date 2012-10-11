
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

    public void addItem(Bitmap leftBmp, String leftTitle, boolean leftIsSub, Bitmap rightBmp,
            String rightTitle, boolean rightIsSub) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("leftImage", leftBmp);
        map.put("leftTitle", leftTitle);
        map.put("leftIsSub", leftIsSub);

        map.put("rightImage", rightBmp);
        map.put("rightTitle", rightTitle);
        map.put("rightIsSub", rightIsSub);
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
            holder.leftImg = (ImageView) conterView.findViewById(R.id.img_left_sub);
            holder.leftTxt = (TextView) conterView.findViewById(R.id.txt_left_sub_title);
            holder.leftBtn = (Button) conterView.findViewById(R.id.btn_left_sub);

            holder.rightImg = (ImageView) conterView.findViewById(R.id.img_right_sub);
            holder.rightTxt = (TextView) conterView.findViewById(R.id.txt_right_sub_title);
            holder.rightBtn = (Button) conterView.findViewById(R.id.btn_right_sub);
            conterView.setTag(holder);
        } else {
            holder = (ViewHolder) conterView.getTag();
        }

        // add left data
        holder.leftImg.setImageBitmap((Bitmap) mData.get(position).get("leftImage"));
        holder.leftTxt.setText(mData.get(position).get("leftTitle").toString());
        boolean leftIsSub = (Boolean) mData.get(position).get("leftIsSub");
        if (leftIsSub) {
            holder.leftBtn.setBackgroundResource(R.drawable.btn_sub_have);
            holder.leftBtn.setText(context.getResources().getString(R.string.sub_have));
            holder.leftBtn.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.leftBtn.setBackgroundResource(R.drawable.btn_sub_no);
            holder.leftBtn.setText(context.getResources().getString(R.string.sub_no));
            holder.leftBtn.setTextColor(context.getResources().getColor(R.color.white));
        }

        // add right data
        holder.rightImg.setImageBitmap((Bitmap) mData.get(position).get("rightImage"));
        holder.rightTxt.setText(mData.get(position).get("rightTitle").toString());
        boolean rightIsSub = (Boolean) mData.get(position).get("rightIsSub");
        if (rightIsSub) {
            holder.rightBtn.setBackgroundResource(R.drawable.btn_sub_have);
            holder.rightBtn.setText(context.getResources().getString(R.string.sub_have));
            holder.rightBtn.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.rightBtn.setBackgroundResource(R.drawable.btn_sub_no);
            holder.rightBtn.setText(context.getResources().getString(R.string.sub_no));
            holder.rightBtn.setTextColor(context.getResources().getColor(R.color.white));
        }

        return conterView;
    }

    static class ViewHolder {

        ImageView leftImg;
        TextView leftTxt;
        Button leftBtn;

        ImageView rightImg;
        TextView rightTxt;
        Button rightBtn;
    }

}
