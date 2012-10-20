
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.util.UrlImageViewHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeadlineAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private ArrayList<PicWithTxtNews> headNews =null;

    public HeadlineAdapter(Context context,ArrayList<PicWithTxtNews> _headNews) {
    	headNews = _headNews;
        mInflater = LayoutInflater.from(context);

    }

    public int getCount() {

        return headNews.size();
    }

    public Object getItem(int position) {

        return headNews.get(position);
    }

    public long getItemId(int position) {

        return position;
    }

    public void setList(ArrayList<PicWithTxtNews> _headNews){
    	headNews = _headNews;
    	notifyDataSetChanged();
    }

    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.headline_item, null);
            holder.txt_title = (TextView) view.findViewById(R.id.txt_headline_item_title);
            holder.txt_body = (TextView) view.findViewById(R.id.txt_headline_item_body);
            holder.img = (ImageView) view.findViewById(R.id.img_headline_item);
            view.setTag(holder);
        } else

        {
            holder = (ViewHolder) view.getTag();
        }

        holder.txt_title.setText(headNews.get(position).getTitle());
        holder.txt_body.setText(headNews.get(position).getDescription());

        //UrlImageViewHelper.setUrlDrawable(holder.img, WH_DMHttpApiV1.URL_DOMAIN+headNews.get(position).getLitpic());
        UrlImageViewHelper.setUrlDrawable(holder.img, WH_DMHttpApiV1.URL_DOMAIN+headNews.get(position).getLitpic(),R.drawable.test1_1,null);
        return view;
    }

    static class ViewHolder {
        public TextView txt_title;

        public TextView txt_body;
        public ImageView img;
    }

}
