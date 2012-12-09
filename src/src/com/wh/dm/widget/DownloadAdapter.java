
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.activity.DownloadActivity;
import com.wh.dm.type.LoadInfo;
import com.wh.dm.util.UrlImageViewHelper;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
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
    private Handler handler;

    private int positionValue;

    public DownloadAdapter(Activity _context, Handler _handler) {

        context = _context;
        inflater = context.getLayoutInflater();
        this.handler = _handler;
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

        positionValue = position;
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.download_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imgDM = (ImageView) convertView.findViewById(R.id.img_sub_manage);
            viewHolder.txtDM = (TextView) convertView.findViewById(R.id.txt_sub_manage_title);
            viewHolder.txtAddition = (TextView) convertView.findViewById(R.id.txt_download_ad);
            viewHolder.btnStatus = (Button) convertView.findViewById(R.id.btn_download_status);
            viewHolder.pbStatus = (ProgressBar) convertView.findViewById(R.id.pbar_download);
            viewHolder.pbStatus.setProgress(dms.get(positionValue).getPro());

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.btnStatus.setId(position);
        viewHolder.btnStatus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                int pos = v.getId();

                if (dms.get(pos).isFinish()) {
                    // send message to open magazine
                    Message msg = new Message();
                    msg.what = DownloadActivity.MSG_OPEN_MAGAZINE;
                    msg.arg1 = dms.get(pos).getSid();
                    handler.sendMessage(msg);

                } else if (!dms.get(pos).isStart()) {
                    // send message to start
                    Message msg = new Message();
                    msg.what = DownloadActivity.MSG_START_LOAD;
                    msg.arg1 = dms.get(pos).getSid();
                    handler.sendMessage(msg);
                } else if (dms.get(pos).isPause()) {
                    // send message to go to
                    Message msg = new Message();
                    msg.what = DownloadActivity.MSG_START_LOAD;
                    msg.arg1 = dms.get(pos).getSid();
                    handler.sendMessage(msg);
                } else {
                    // send message to pause
                    Message msg = new Message();
                    msg.what = DownloadActivity.MSG_PAUSE_LOAD;
                    msg.arg1 = dms.get(pos).getSid();
                    handler.sendMessage(msg);
                }

            }
        });

        UrlImageViewHelper.setUrlDrawable(viewHolder.imgDM,
                WH_DMHttpApiV1.URL_DOMAIN + dms.get(position).getPicPath(),
                R.drawable.subscription_manage_background, null);
        viewHolder.txtDM.setText(dms.get(position).getTitle());

        if (dms.get(position).isFinish()) {
            viewHolder.pbStatus.setVisibility(View.GONE);
            viewHolder.txtAddition.setVisibility(View.VISIBLE);
            viewHolder.txtAddition.setText("下载已完成");
            viewHolder.btnStatus.setText("查看DM");
        } else if (!dms.get(position).isStart()) {
            viewHolder.pbStatus.setVisibility(View.GONE);
            viewHolder.txtAddition.setVisibility(View.VISIBLE);
            viewHolder.txtAddition.setText("未下载杂志");
            viewHolder.btnStatus.setText("开始下载");
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
        private Button btnStatus;
        private ProgressBar pbStatus;

    }

}
