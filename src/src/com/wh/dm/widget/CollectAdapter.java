
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.type.FavoriteNews;
import com.wh.dm.util.UrlImageViewHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CollectAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    private ArrayList<FavoriteNews> list = null;
    private List<Integer> list_checked = new ArrayList<Integer>();// checked
                                                                         // item
                                                                         // position

    public CollectAdapter(Context context) {

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

    public void setList(ArrayList<FavoriteNews> _fav) {

        list = _fav;
        notifyDataSetChanged();
    }

    public void addList(ArrayList<FavoriteNews> _fav) {

        list.addAll(_fav);
        notifyDataSetChanged();
    }

    public ArrayList<FavoriteNews> getList() {

        return list;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.dm_collect_item1, null);
            holder.txt_collect_title = (TextView) view.findViewById(R.id.txt_collect_title1);
            holder.txt_collect_body = (TextView) view.findViewById(R.id.txt_collect_body1);
            holder.check = (CheckBox) view.findViewById(R.id.cb_collect);
            holder.img1 = (ImageView) view.findViewById(R.id.iv_collect);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.txt_collect_title.setText(list.get(position).getTitle().toString());
        holder.txt_collect_body.setText(list.get(position).getPubdate().toString());

        holder.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // TODO Auto-generated method stub
                if (isChecked)
                    list_checked.add(Integer.valueOf(position));
                if (!isChecked)
                    list_checked.remove(Integer.valueOf(position));
            }

        });
        holder.check.setChecked(list_checked.contains(Integer.valueOf(position)) ? true : false);
        // load image
        if (WH_DMApp.isLoadImg) {
            UrlImageViewHelper.setUrlDrawable(holder.img1,
                    WH_DMHttpApiV1.URL_DOMAIN + list.get(position).getLitpic().toString(),
                    R.drawable.item_default, null);
        } else {
            holder.img1.setBackgroundResource(R.drawable.item_default);
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

    public List<Integer> getCheckedID() {

        return list_checked;
    }

    public void RemoveAllInCheckedList() {

        list_checked.clear();
    }

}
