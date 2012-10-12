package com.wh.dm.widget;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.wh.dm.R;
import com.wh.dm.type.Cover;

public class DownloadAdapter extends BaseAdapter {
	private Activity context;
	private LayoutInflater inflater;
	private ArrayList<HashMap<String,Object>> dms;

	public DownloadAdapter(Activity _context){
		context = _context;
		inflater = context.getLayoutInflater();
		dms = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map1 = new HashMap<String,Object>();
		map1.put("title", "中百仓储");
		map1.put("num", "22496人下载");
		map1.put("addition", "下载已完成");
		map1.put("btnStatus","查看DM");
		map1.put("status", false);
		dms.add(map1);
		HashMap<String,Object> map2 = new HashMap<String,Object>();
		map2.put("title", "Dior");
		map2.put("num", "22496人下载");
		map2.put("addition", "暂停下载");
		map2.put("btnStatus","继续下载");
		map2.put("status", false);
		dms.add(map2);
		HashMap<String,Object> map3 = new HashMap<String,Object>();
		map3.put("title", "柏氏专卖");
		map3.put("num", "22496人下载");
		map3.put("addition", "暂停下载");
		map3.put("btnStatus","继续下载");
		map3.put("status", false);
		dms.add(map3);

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dms.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return (Object)dms.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.download_item, null);
			viewHolder = new ViewHolder();
			viewHolder.imgDM = (ImageView)convertView.findViewById(R.id.img_sub_manage);
			viewHolder.txtDM = (TextView)convertView.findViewById(R.id.txt_sub_manage_title);
			viewHolder.txtAddition =(TextView)convertView.findViewById(R.id.txt_download_ad);
			viewHolder.txtNum =(TextView)convertView.findViewById(R.id.txt_download_total);
			viewHolder.btnStatus =(Button)convertView.findViewById(R.id.btn_download_status);
			viewHolder.pbStatus =(ProgressBar)convertView.findViewById(R.id.pbar_download);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.txtDM.setText((String)dms.get(position).get("title"));
		viewHolder.txtAddition.setText((String)dms.get(position).get("addition"));
		viewHolder.txtNum.setText((String)dms.get(position).get("num"));
		viewHolder.btnStatus.setText((String)dms.get(position).get("btnStatus"));
		if((Boolean)dms.get(position).get("status")){
			viewHolder.pbStatus.setVisibility(View.VISIBLE);
			viewHolder.txtAddition.setVisibility(View.GONE);
		}else{
			viewHolder.pbStatus.setVisibility(View.GONE);
			viewHolder.txtAddition.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	static class ViewHolder{
		private ImageView imgDM;
		private TextView txtDM;
		private TextView txtAddition;
		private TextView txtNum;
		private Button btnStatus;
		private ProgressBar pbStatus;

	}
}
