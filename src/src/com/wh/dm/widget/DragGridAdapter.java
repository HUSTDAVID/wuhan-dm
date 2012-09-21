
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.type.Cover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DragGridAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Cover> list;
    private RelativeLayout relate_dm;
    private TextView txt_String;
    private ImageView img_String;

    public DragGridAdapter(Context mContext, ArrayList<Cover> list) {

        this.context = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public void exchange(int startPosition, int endPosition) {

        Object endObject = getItem(endPosition);
        Object startObject = getItem(startPosition);
        list.add(startPosition, (Cover) endObject);
        list.remove(startPosition + 1);
        list.add(endPosition, (Cover) startObject);
        list.remove(endPosition + 1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
        TextView txt = (TextView) convertView.findViewById(R.id.item_text);
        ImageView img = (ImageView) convertView.findViewById(R.id.item_img);
        relate_dm = (RelativeLayout) convertView.findViewById(R.id.item_relate);
        txt.setText(list.get(position).getName());
        img.setImageResource(list.get(position).getDrawableId());
        if (list.get(position).getId() == 0) {
            relate_dm.setBackgroundResource(R.drawable.red);
        } else if (list.get(position).getId() == 1) {
            relate_dm.setBackgroundResource(R.drawable.red);
        }

        return convertView;
    }

}
