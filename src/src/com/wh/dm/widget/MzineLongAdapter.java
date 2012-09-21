
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
import java.util.TreeSet;

public class MzineLongAdapter extends BaseAdapter {

    private static final int TYPE_IMG_ITEM1 = 0;
    private static final int TYPE_IMG_ITEM2 = 1;
    private static final int TYPE_MAX_COUNT = 2;

    private final LayoutInflater mInflater;
    private final ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
    private final TreeSet mSeparatorsSet = new TreeSet();

    public MzineLongAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
    }

    public void addItemType1(Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("img1_bottom1", bmp1);
        map.put("img2_bottom1", bmp2);
        map.put("img3_bottom1", bmp3);
        map.put("img4_bottom1", bmp4);

        mData.add(map);
        notifyDataSetChanged();
    }

    public void addItemType2(Bitmap bmp1, Bitmap bmp2, Bitmap bmp3) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("img1_bottom2", bmp1);
        map.put("img2_bottom2", bmp2);
        map.put("img3_bottom2", bmp3);

        mData.add(map);
        mSeparatorsSet.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        return mSeparatorsSet.contains(position) ? TYPE_IMG_ITEM1 : TYPE_IMG_ITEM2;
    }

    @Override
    public int getViewTypeCount() {

        return TYPE_MAX_COUNT;
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
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case TYPE_IMG_ITEM1:
                    convertView = mInflater.inflate(R.layout.dm_mzine_long_bottom1, null);
                    holder.img1_bottom1 = (ImageView) convertView
                            .findViewById(R.id.img_mzine_long_bottom1_1);
                    holder.img2_bottom1 = (ImageView) convertView
                            .findViewById(R.id.img_mzine_long_bottom1_2);
                    holder.img3_bottom1 = (ImageView) convertView
                            .findViewById(R.id.img_mzine_long_bottom1_3);
                    holder.img4_bottom1 = (ImageView) convertView
                            .findViewById(R.id.img_mzine_long_bottom1_4);

                    break;
                case TYPE_IMG_ITEM2:
                    convertView = mInflater.inflate(R.layout.dm_mzine_long_bottom2, null);
                    holder.img1_bottom2 = (ImageView) convertView
                            .findViewById(R.id.img_mzine_long_bottom2_1);
                    holder.img2_bottom2 = (ImageView) convertView
                            .findViewById(R.id.img_mzine_long_bottom2_2);
                    holder.img3_bottom2 = (ImageView) convertView
                            .findViewById(R.id.img_mzine_long_bottom2_3);

                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (type) {
            case TYPE_IMG_ITEM1:
                holder.img1_bottom1
                        .setImageBitmap((Bitmap) mData.get(position).get("img1_bottom1"));
                holder.img2_bottom1
                        .setImageBitmap((Bitmap) mData.get(position).get("img2_bottom1"));
                holder.img3_bottom1
                        .setImageBitmap((Bitmap) mData.get(position).get("img3_bottom1"));
                holder.img4_bottom1
                        .setImageBitmap((Bitmap) mData.get(position).get("img4_bottom1"));
                break;

            case TYPE_IMG_ITEM2:
                holder.img1_bottom2
                        .setImageBitmap((Bitmap) mData.get(position).get("img1_bottom2"));
                holder.img2_bottom2
                        .setImageBitmap((Bitmap) mData.get(position).get("img2_bottom2"));
                holder.img3_bottom2
                        .setImageBitmap((Bitmap) mData.get(position).get("img3_bottom2"));
                break;
        }

        return convertView;
    }

    public static class ViewHolder {
        ImageView img1_bottom1;
        ImageView img2_bottom1;
        ImageView img3_bottom1;
        ImageView img4_bottom1;

        ImageView img1_bottom2;
        ImageView img2_bottom2;
        ImageView img3_bottom2;

    }

}
