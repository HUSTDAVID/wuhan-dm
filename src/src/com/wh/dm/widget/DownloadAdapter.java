
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.type.LoadInfo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DownloadAdapter extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<LoadInfo> dms;

    public DownloadAdapter(Activity _context) {

        context = _context;
        inflater = context.getLayoutInflater();
        dms = new ArrayList<LoadInfo>();
    }

    public void addItem(LoadInfo item) {

        dms.add(item);
        notifyDataSetChanged();
    }

    public void setList(ArrayList<LoadInfo> dms) {

        this.dms = dms;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        // TODO Auto-generated method stub
        return dms.size();
    }

    @Override
    public Object getItem(int position) {

        // TODO Auto-generated method stub
        return (Object) dms.get(position);
    }

    @Override
    public long getItemId(int position) {

        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.download_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imgDM = (ImageView) convertView.findViewById(R.id.img_sub_manage);
            viewHolder.txtDM = (TextView) convertView.findViewById(R.id.txt_sub_manage_title);
            viewHolder.txtAddition = (TextView) convertView.findViewById(R.id.txt_download_ad);
            viewHolder.txtNum = (TextView) convertView.findViewById(R.id.txt_download_total);
            viewHolder.btnStatus = (Button) convertView.findViewById(R.id.btn_download_status);
            viewHolder.pbStatus = (ProgressBar) convertView.findViewById(R.id.pbar_download);
            viewHolder.btnStatus.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (dms.get(pos).isFinish()) {

                    } else if (dms.get(pos).isPause()) {
                        dms.get(pos).setPause(false);
                        notifyDataSetChanged();
                    } else {
                        dms.get(pos).setPause(true);
                        notifyDataSetChanged();
                    }

                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imgDM.setImageBitmap(dms.get(position).getBmpLogo());
        viewHolder.txtDM.setText(dms.get(position).getTitle());
        viewHolder.txtNum.setText(dms.get(position).getNum());
        if (dms.get(position).isFinish()) {
            viewHolder.pbStatus.setVisibility(View.GONE);
            viewHolder.txtAddition.setVisibility(View.VISIBLE);
            viewHolder.txtAddition.setText("下载已完成");
            viewHolder.btnStatus.setText("查看DM");
        } else if (dms.get(position).isPause()) {
            viewHolder.pbStatus.setVisibility(View.GONE);
            viewHolder.txtAddition.setVisibility(View.VISIBLE);
            viewHolder.txtAddition.setText("暂停下载");
            viewHolder.btnStatus.setText("继续下载");
        } else {
            viewHolder.pbStatus.setVisibility(View.VISIBLE);
            viewHolder.txtAddition.setVisibility(View.GONE);
            viewHolder.btnStatus.setText("暂停下载");
            viewHolder.pbStatus.setProgress(dms.get(position).getPro());
        }
        return convertView;
    }

    static class ViewHolder {
        private ImageView imgDM;
        private TextView txtDM;
        private TextView txtAddition;
        private TextView txtNum;
        private Button btnStatus;
        private ProgressBar pbStatus;

    }
}
