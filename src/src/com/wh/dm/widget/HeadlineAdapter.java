
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.util.UrlImageViewHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HeadlineAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private ArrayList<PicWithTxtNews> headNews = null;
    private final Context context;

    public HeadlineAdapter(Context _context) {

        mInflater = LayoutInflater.from(_context);
        context = _context;

    }

    @Override
    public int getCount() {

        if (headNews == null) {
            return 0;
        } else {
            return headNews.size();
        }
    }

    @Override
    public Object getItem(int position) {

        return headNews.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public void setList(ArrayList<PicWithTxtNews> _headNews) {

        headNews = _headNews;
        notifyDataSetChanged();
    }

    public void addList(ArrayList<PicWithTxtNews> _headNews) {

        headNews.addAll(_headNews);
        notifyDataSetChanged();
    }

    public ArrayList<PicWithTxtNews> getList() {

        return headNews;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.headline_item, null);
            holder.txt_title = (TextView) view.findViewById(R.id.txt_headline_item_title);
            holder.txt_body = (TextView) view.findViewById(R.id.txt_headline_item_body);
            holder.txt_reply = (TextView) view.findViewById(R.id.txt_headline_reply);
            holder.img = (ImageView) view.findViewById(R.id.img_headline_item);
            view.setTag(holder);
        } else

        {
            holder = (ViewHolder) view.getTag();
        }

        holder.txt_title.setText(headNews.get(position).getTitle());
        holder.txt_body.setText(headNews.get(position).getDescription());
        holder.txt_reply.setText("" + headNews.get(position).getFcount()
                + context.getString(R.string.news_reply_count));
        if (WH_DMApp.isLoadImg) {
            UrlImageViewHelper.setUrlDrawable(holder.img,
                    WH_DMHttpApiV1.URL_DOMAIN + headNews.get(position).getLitpic(),
                    R.drawable.news_src, null);
        } else {
            holder.img.setBackgroundResource(R.drawable.news_src);
        }
        return view;
    }

    static class ViewHolder {
        public TextView txt_title;
        public TextView txt_body;
        public TextView txt_reply;
        public ImageView img;
    }

}
