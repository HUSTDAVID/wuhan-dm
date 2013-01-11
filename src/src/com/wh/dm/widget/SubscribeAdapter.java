
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.TwoMagazine;
import com.wh.dm.util.UrlImageViewHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SubscribeAdapter extends BaseAdapter {

    public static final int DM_PIC = 0;
    public static final int DM_CON = 2;
    ArrayList<TwoMagazine> twoMagazine;
    LayoutInflater mInflater;
    Context context;
    private Handler handler;
    private final int MSG_SUBCRIBE = 1;
    private DatabaseImpl databaseImpl;

    public SubscribeAdapter(Context context) {

        this.context = context;
        twoMagazine = new ArrayList<TwoMagazine>();
        mInflater = LayoutInflater.from(context);
    }

    public void setHandler(Handler _handler) {

        handler = _handler;
    }

    public void setDatabaseImpl(DatabaseImpl _databaseImpl) {

        databaseImpl = _databaseImpl;
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

    public Magazine getLeft(int position) {

        return twoMagazine.get(position).getLeftMagazine();
    }

    public Magazine getRight(int position) {

        return twoMagazine.get(position).getRightMagazine();
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
            holder.sub_right = (RelativeLayout) conterView.findViewById(R.id.sub_right);
            conterView.setTag(holder);
        } else {
            holder = (ViewHolder) conterView.getTag();
        }

        final Magazine left;
        final Magazine right;
        left = twoMagazine.get(position).getLeftMagazine();
        right = twoMagazine.get(position).getRightMagazine();

        // add left data
        holder.leftTxt.setText(left.getSname());
        String leftStatus = left.getEditor();
        if (leftStatus != null && leftStatus.equals("subcribed")) {
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

        String rightStatus = right.getEditor();
        if (rightStatus != null && rightStatus.equals("subcribed")) {
            holder.rightBtn.setBackgroundResource(R.drawable.btn_sub_have);
            holder.rightBtn.setText(context.getResources().getString(R.string.sub_have));
            holder.rightBtn.setTextColor(context.getResources().getColor(R.color.black));

        } else {
            holder.rightBtn.setBackgroundResource(R.drawable.btn_sub_no);
            holder.rightBtn.setText(context.getResources().getString(R.string.sub_no));
            holder.rightBtn.setTextColor(context.getResources().getColor(R.color.white));
        }
        final ViewHolder tmpHolder = holder;
        holder.leftBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (WH_DMApp.isConnected) {
                    Message message = new Message();
                    message.what = MSG_SUBCRIBE;
                    Bundle bundle = new Bundle();
                    bundle.putInt("cid", left.getSid());
                    message.setData(bundle);
                    handler.sendMessage(message);

                    tmpHolder.leftBtn.setBackgroundResource(R.drawable.btn_sub_have);
                    tmpHolder.leftBtn.setText(context.getResources().getString(R.string.sub_have));
                    tmpHolder.leftBtn.setTextColor(context.getResources().getColor(R.color.black));
                    left.setEditor("subcribed");
                    databaseImpl.addMagazine(left);
                    context.sendBroadcast(new Intent(WH_DMApp.INTENT_ACTION_SUBCRIBE_CHANGE));

                }

            }

        });
        holder.leftImg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }

        });
        holder.rightBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (WH_DMApp.isConnected) {
                    Message message = new Message();
                    message.what = MSG_SUBCRIBE;
                    Bundle bundle = new Bundle();
                    bundle.putInt("cid", right.getSid());
                    message.setData(bundle);
                    handler.sendMessage(message);

                    tmpHolder.rightBtn.setBackgroundResource(R.drawable.btn_sub_have);
                    tmpHolder.rightBtn.setText(context.getResources().getString(R.string.sub_have));
                    tmpHolder.rightBtn.setTextColor(context.getResources().getColor(R.color.black));
                    right.setEditor("subcribed");
                    databaseImpl.addMagazine(right);
                    context.sendBroadcast(new Intent(WH_DMApp.INTENT_ACTION_SUBCRIBE_CHANGE));

                }
            }

        });
        holder.rightImg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }

        });

        if (right.getAddtime().equals("END")) {
            holder.sub_right.setVisibility(View.INVISIBLE);
        } else {
            holder.sub_right.setVisibility(View.VISIBLE);
        }

        if (WH_DMApp.isLoadImg) {
            UrlImageViewHelper.setUrlDrawable(holder.leftImg,
                    WH_DMHttpApiV1.URL_DOMAIN + left.getPic(),
                    R.drawable.subscription_bigimg_background, null);
            UrlImageViewHelper.setUrlDrawable(holder.rightImg,
                    WH_DMHttpApiV1.URL_DOMAIN + right.getPic(),
                    R.drawable.subscription_bigimg_background, null);
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
        RelativeLayout sub_right;
    }

}
