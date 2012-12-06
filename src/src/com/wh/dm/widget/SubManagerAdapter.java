
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.type.Magazine;
import com.wh.dm.util.UrlImageViewHelper;

import android.content.Context;
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

public class SubManagerAdapter extends BaseAdapter {

    private ArrayList<Magazine> magazines;
    private final LayoutInflater mInflater;
    private final Context context;
    private Handler handler;
    private static final int MSG_UNSUBCRIBE = 1;

    public SubManagerAdapter(Context context) {

        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setHandler(Handler _handler) {

        handler = _handler;
    }

    public void setList(ArrayList<Magazine> _magazines) {

        magazines = _magazines;
        notifyDataSetChanged();
    }

    public ArrayList<Magazine> getList() {

        return magazines;
    }

    public void clear() {

        if (magazines != null) {
            magazines.clear();
            notifyDataSetChanged();
        }

    }

    @Override
    public int getCount() {

        if (magazines == null) {
            return 0;
        } else {
            return magazines.size();
        }

    }

    @Override
    public Object getItem(int position) {

        return magazines.get(position);
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

        UrlImageViewHelper.setUrlDrawable(holder.img,
                WH_DMHttpApiV1.URL_DOMAIN + magazines.get(position).getSpic(),
                R.drawable.subscription_manage_background, null);
        holder.txtTitie.setText(magazines.get(position).getSname());
        final int pos = position;
        holder.btnNoSub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("id", magazines.get(pos).getSid());
                message.setData(bundle);
                message.what = MSG_UNSUBCRIBE;
                handler.sendMessage(message);
                if (WH_DMApp.isLogin) {
                    magazines.remove(pos);
                    notifyDataSetChanged();
                }

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
