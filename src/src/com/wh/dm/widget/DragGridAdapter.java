
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.type.DM;

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
    private final ArrayList<DM> list;
    private RelativeLayout relate_dm;
    private TextView txt_dm;
    private ImageView img_dm;

    public DragGridAdapter(Context mContext, ArrayList<DM> list) {

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
        list.add(startPosition, (DM) endObject);
        list.remove(startPosition + 1);
        list.add(endPosition, (DM) startObject);
        list.remove(endPosition + 1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
        txt_dm = (TextView) convertView.findViewById(R.id.item_text);
        img_dm = (ImageView) convertView.findViewById(R.id.item_img);
        relate_dm = (RelativeLayout) convertView.findViewById(R.id.item_relate);
        txt_dm.setText(list.get(position).getDM());
        if (list.get(position).getId() != 0) {
            img_dm.setImageResource(list.get(position).getId());
        }
        if (list.get(position).getRed()) {
            relate_dm.setBackgroundResource(R.drawable.red);
        }
        return convertView;
    }

}
