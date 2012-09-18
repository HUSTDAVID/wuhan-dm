
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubManagerAdapter extends BaseAdapter {

    ArrayList<Map<String, Object>> mData;
    LayoutInflater mInflater;
    Context context;

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

        holder.img.setImageBitmap((Bitmap) mData.get(position).get("image"));
        holder.txtAd.setText(mData.get(position).get("ad").toString());
        holder.txtTitie.setText(mData.get(position).get("title").toString());
        holder.txtTotal.setText(mData.get(position).get("subTotal").toString());

        holder.btnNoSub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(context, "È¡Ïû¶©ÔÄ", Toast.LENGTH_SHORT).show();

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
