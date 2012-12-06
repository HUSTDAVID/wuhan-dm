package com.wh.dm.widget;

import java.util.ArrayList;
import java.util.List;

import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.activity.PhotosDetailsActivity;
import com.wh.dm.type.FavoritePhoto;
import com.wh.dm.util.UrlImageViewHelper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;



public class PhotoCollectAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<FavoritePhoto> list = null;
	private List<Integer> list_checked = new ArrayList<Integer>();
	
	public PhotoCollectAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setList(ArrayList<FavoritePhoto> _fav) {

        list = _fav;
        notifyDataSetChanged();
    }

    public void addList(ArrayList<FavoritePhoto> _fav) {

        list.addAll(_fav);
        notifyDataSetChanged();
    }

    public ArrayList<FavoritePhoto> getList() {

        return list;
    }
    
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.dm_collect_item2, null);
            holder.txt_collect_title = (TextView) view.findViewById(R.id.txt_photo_title);
            holder.txt_collect_addtime = (TextView) view.findViewById(R.id.txt_photo_addtime);
            holder.img_collect_pic = (ImageView) view.findViewById(R.id.img_photo_item);
            holder.check=(CheckBox)view.findViewById(R.id.cb_photo_collect);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.txt_collect_title.setText(list.get(position).getAname().toString());
        holder.txt_collect_addtime.setText(list.get(position).getAddtime().toString());
        
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
            UrlImageViewHelper.setUrlDrawable(holder.img_collect_pic,
                    WH_DMHttpApiV1.URL_DOMAIN + list.get(position).getLitpic().toString(),
                    R.drawable.item_default, null);
        } else {
            holder.img_collect_pic.setBackgroundResource(R.drawable.item_default);
        }
        
        holder.img_collect_pic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            	 Intent intent = new Intent(context, PhotosDetailsActivity.class);
                 intent.putExtra("aid", list.get(position).getAid());
                 context.startActivity(intent);
            }
        });
        
        
        return view;
	}

	static class ViewHolder {
        public TextView txt_collect_title;
        public ImageView img_collect_pic;
        public TextView txt_collect_addtime;
        public CheckBox check;
    }
	
	 public List<Integer> getCheckedID() {

	        return list_checked;
	  }

	  public void RemoveAllInCheckedList() {

	        list_checked.clear();
	  }
}
