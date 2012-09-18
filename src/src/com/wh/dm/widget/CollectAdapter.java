
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class CollectAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Map<String, Object>> list;

    public CollectAdapter(Context context, List<Map<String, Object>> list) {

        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
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

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.dm_collect_item1, null);
            holder.txt_collect_title = (TextView) view.findViewById(R.id.txt_collect_title1);
            holder.txt_collect_body = (TextView) view.findViewById(R.id.txt_collect_body1);
            holder.check = (CheckBox) view.findViewById(R.id.cb_collect);
            view.setTag(holder);
        } else

        {
            holder = (ViewHolder) view.getTag();
        }

        holder.txt_collect_title.setText(list.get(position).get("txt_collect_title").toString());
        holder.txt_collect_body.setText(list.get(position).get("txt_collect_body").toString());
        boolean check = (Boolean) list.get(position).get("check");
        if (check) {
            holder.check.setChecked(true);
        }

        return view;
    }

    static class ViewHolder {
        public TextView txt_collect_title;
        public TextView txt_collect_body;
        public CheckBox check;
        public ImageView img1;
    }

    public void addItemType(String string) {

        // TODO Auto-generated method stub

    }

}
