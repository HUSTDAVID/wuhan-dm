package com.wh.dm.widget;

import com.wh.dm.R;
import java.util.ArrayList;
import java.util.TreeSet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MutiItemAdapter extends BaseAdapter {

    private static final int TYPE_IMG_ITEM1 = 0;
    private static final int TYPE_IMG_ITEM2 = 1;
    private static final int TYPE_MAX_COUNT = 2;
    
    private final LayoutInflater mInflater;
    private final  ArrayList<String> mData = new ArrayList();
    private final TreeSet mSeparatorsSet = new TreeSet();

    public MutiItemAdapter(Context context) {
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
            switch (type) {
                case TYPE_IMG_ITEM1:
                    convertView = mInflater.inflate(R.layout.dm_img_item1, null);
                    holder.img1 = (ImageView)convertView.findViewById(R.id.img1_left);
                    holder.img2 = (ImageView)convertView.findViewById(R.id.img1_right);
                    break;
                case TYPE_IMG_ITEM2:
                    convertView = mInflater.inflate(R.layout.dm_img_item2, null);
                    holder.img1 = (ImageView)convertView.findViewById(R.id.img2_left);
                    holder.img2 = (ImageView)convertView.findViewById(R.id.img2_mid);
                    holder.img3 = (ImageView)convertView.findViewById(R.id.img2_right);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    public static class ViewHolder {
        public ImageView img1;
        public ImageView img2;
        public ImageView img3;
    }

}
