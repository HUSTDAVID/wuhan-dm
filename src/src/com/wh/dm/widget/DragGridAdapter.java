
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.type.Magazine;
import com.wh.dm.util.UrlImageViewHelper;

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
    private final ArrayList<Magazine> list;
    private RelativeLayout relate_dm;
    private TextView txt_String;
    private ImageView img_String;

    public DragGridAdapter(Context mContext, ArrayList<Magazine> list) {

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
        list.add(startPosition, (Magazine) endObject);
        list.remove(startPosition + 1);
        list.add(endPosition, (Magazine) startObject);
        list.remove(endPosition + 1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
        TextView txt = (TextView) convertView.findViewById(R.id.item_text);
        ImageView img = (ImageView) convertView.findViewById(R.id.item_img);
        relate_dm = (RelativeLayout) convertView.findViewById(R.id.item_relate);
        int flag = list.get(position).getDrawableId();
        if (flag != 0) {
            txt.setText(list.get(position).getName());
        } else {
            txt.setText(list.get(position).getSname());
        }

        if (flag != 0) {
            img.setImageResource(list.get(position).getDrawableId());
        } else {
            UrlImageViewHelper.setUrlDrawable(img, WH_DMHttpApiV1.URL_DOMAIN
                    + list.get(position).getSpic(), R.drawable.t2, null);
        }
        if (list.get(position).getId() == 1 || list.get(position).getId() == 2) {
            relate_dm.setBackgroundResource(R.drawable.index_bg_orange);
        } else {
            relate_dm.setBackgroundResource(R.drawable.index_bg_blue);
        }
        return convertView;
    }
}
