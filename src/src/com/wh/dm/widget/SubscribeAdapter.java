
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.TwoMagazine;
import com.wh.dm.util.UrlImageViewHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SubscribeAdapter extends BaseAdapter {

    ArrayList<TwoMagazine> twoMagazine;
    LayoutInflater mInflater;
    Context context;

    public SubscribeAdapter(Context context) {

        this.context = context;
        twoMagazine = new ArrayList<TwoMagazine>();
        mInflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<TwoMagazine> _twoMagazine) {

        twoMagazine = _twoMagazine;
        notifyDataSetChanged();
    }

    public void addList(ArrayList<TwoMagazine> _twoMagazine) {

        twoMagazine.addAll(_twoMagazine);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return twoMagazine.size();
    }

    @Override
    public Object getItem(int position) {

        return twoMagazine.get(position);
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

        Magazine left;
        Magazine right;
        left = twoMagazine.get(position).getLeftMagazine();
        right = twoMagazine.get(position).getRightMagazine();
        // add left data

        holder.leftTxt.setText(left.getSname());
        // test data
        boolean leftIsSub = false;
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

        holder.rightTxt.setText(right.getSname());
        // test data
        boolean rightIsSub = false;
        if (rightIsSub) {
            holder.rightBtn.setBackgroundResource(R.drawable.btn_sub_have);
            holder.rightBtn.setText(context.getResources().getString(R.string.sub_have));
            holder.rightBtn.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.rightBtn.setBackgroundResource(R.drawable.btn_sub_no);
            holder.rightBtn.setText(context.getResources().getString(R.string.sub_no));
            holder.rightBtn.setTextColor(context.getResources().getColor(R.color.white));
        }

        if (WH_DMApp.isLoadImg) {
            UrlImageViewHelper.setUrlDrawable(holder.leftImg,
                    WH_DMHttpApiV1.URL_DOMAIN + left.getPic(), R.drawable.item_default, null);
            UrlImageViewHelper.setUrlDrawable(holder.rightImg,
                    WH_DMHttpApiV1.URL_DOMAIN + right.getPic(), R.drawable.item_default, null);
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
