
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.type.Magazine;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubManagerAdapter extends BaseAdapter {

    private final ArrayList<Map<String, Object>> mData;
    private final LayoutInflater mInflater;
    private final Context context;
    private Handler handler;
    private static final int MSG_UNSUBCRIBE = 1;

    public SubManagerAdapter(Context context) {

        this.context = context;
        mData = new ArrayList<Map<String, Object>>();
        mInflater = LayoutInflater.from(context);
    }

    public void addItem(Bitmap bmp, String title, String subTotal, String ad) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("image", bmp);
        map.put("title", title);
        map.put("subTotal", subTotal);
        map.put("ad", ad);
        mData.add(map);
        notifyDataSetChanged();
    }

    public void setHandler(Handler _handler) {

        handler = _handler;
    }

    public void setList(ArrayList<Magazine> magazines) {

        mData.clear();
        for (int i = 0; i < magazines.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Magazine magazine = magazines.get(i);
            map.put("id", magazine.getSid());
            map.put("image", magazine.getSpic());
            map.put("title", magazine.getSname());
            mData.add(map);
        }
        notifyDataSetChanged();
    }

    public ArrayList<Map<String, Object>> getList() {

        return mData;
    }

    public void clear() {

        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        if (mData == null) {
            mData.clear();
            return 0;
        } else {
            return mData.size();
        }

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
    public View getView(int position, View converView, ViewGroup parent) {

        ViewHolder holder = null;
        if (converView == null) {
            holder = new ViewHolder();
            converView = mInflater.inflate(R.layout.sub_manage_item, null);
            holder.img = (ImageView) converView.findViewById(R.id.img_sub_manage);
            holder.txtAd = (TextView) converView.findViewById(R.id.txt_sub_manage_ad);
            holder.txtTotal = (TextView) converView.findViewById(R.id.txt_sub_manage_total);
            holder.txtTitie = (TextView) converView.findViewById(R.id.txt_sub_manage_title);
            holder.btnNoSub = (Button) converView.findViewById(R.id.btn_sub_manage);

            converView.setTag(holder);
        } else {
            holder = (ViewHolder) converView.getTag();
        }

        // holder.img.setImageBitmap((Bitmap) mData.get(position).get("image"));
        // holder.txtAd.setText(mData.get(position).get("ad").toString());
        holder.txtTitie.setText(mData.get(position).get("title").toString());
        // holder.txtTotal.setText(mData.get(position).get("subTotal").toString());
        final int pos = position;
        holder.btnNoSub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("id", Integer.parseInt(mData.get(pos).get("id").toString()));
                message.setData(bundle);
                message.what = MSG_UNSUBCRIBE;
                handler.sendMessage(message);

            }
        });

        return converView;
    }

    static class ViewHolder {
        ImageView img;
        TextView txtTitie;
        TextView txtTotal;
        TextView txtAd;
        Button btnNoSub;
    }

}
