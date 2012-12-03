
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.type.VoteResultPercent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class VoteResultAdapter extends BaseAdapter {

    ArrayList<VoteResultPercent> list;
    LayoutInflater mInflater;
    Context context;
    Random random;

    public VoteResultAdapter(Context context) {

        this.context = context;
        mInflater = LayoutInflater.from(context);
        random = new Random(4);
    }

    public void setList(ArrayList<VoteResultPercent> _list) {

        this.list = _list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.vote_result, null);
            holder.txtItem = (TextView) convertView.findViewById(R.id.txt_vote_result);
            holder.txtPercent = (TextView) convertView.findViewById(R.id.txt_vote_percent);
            holder.progressbar = (ProgressBar) convertView.findViewById(R.id.pro_vote_result);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int order = position + 1;
        holder.txtItem.setText(order + "." + list.get(position).getItem());
        holder.txtPercent.setText(list.get(position).getPercent() + "%");
        LayoutParams params = holder.progressbar.getLayoutParams();
        params.width = (int) (list.get(position).getPercent() * 3.5);
        holder.progressbar.setLayoutParams(params);
        holder.progressbar = setProColor(holder.progressbar, position);

        return convertView;
    }

    static class ViewHolder {
        TextView txtItem;
        TextView txtPercent;
        ProgressBar progressbar;
    }

    private ProgressBar setProColor(ProgressBar pro, int index) {

        index = index % 5;
        switch (index) {
            case 0:
                pro.setProgressDrawable(context.getResources().getDrawable(
                        R.drawable.vote_scroll_blue));
                break;
            case 1:
                pro.setProgressDrawable(context.getResources().getDrawable(
                        R.drawable.vote_scroll_green));
                break;
            case 2:
                pro.setProgressDrawable(context.getResources().getDrawable(
                        R.drawable.vote_scroll_red));
                break;
            case 3:
                pro.setProgressDrawable(context.getResources().getDrawable(
                        R.drawable.vote_scroll_orange));
                break;
            case 4:
                pro.setProgressDrawable(context.getResources().getDrawable(
                        R.drawable.vote_scroll_purple));
                break;
        }
        return pro;
    }
}
