
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.TreeSet;

public class MzineLongAdapter extends BaseAdapter {

    private static final int TYPE_IMG_ITEM1 = 0;
    private static final int TYPE_IMG_ITEM2 = 1;
    private static final int TYPE_MAX_COUNT = 2;

    private final LayoutInflater mInflater;
    private final ArrayList<String> mData = new ArrayList();
    private final TreeSet mSeparatorsSet = new TreeSet();

    public MzineLongAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
    }

    public void addItemType1(String item) {

        mData.add(item);
        notifyDataSetChanged();
    }

    public void addItemType2(String item) {

        mData.add(item);
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
            convertView = mInflater.inflate(R.layout.dm_mzine_header, null);
            holder.img1 = (ImageView) convertView.findViewById(R.id.mzine_img_header_imageView1);
            holder.img2 = (ImageView) convertView.findViewById(R.id.mzine_img_header_imageView2);
            holder.img3 = (ImageView) convertView.findViewById(R.id.mzine_img_header_imageView3);
            holder.view1 = (ImageView) convertView.findViewById(R.id.mzine_img_header_line1);
            holder.view2 = (ImageView) convertView.findViewById(R.id.mzine_img_header_line2);
            holder.view3 = (ImageView) convertView.findViewById(R.id.mzine_img_header_line3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public static class ViewHolder {
        public ImageView img1;
        public ImageView img2;
        public ImageView img3;
        public View view1;
        public View view2;
        public View view3;

    }

}
