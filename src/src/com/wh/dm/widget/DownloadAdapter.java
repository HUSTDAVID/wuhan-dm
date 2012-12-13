
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
                    msg.arg2 = pos;
                    handler.sendMessage(msg);

                } else if (!dms.get(pos).isStart()) {
                    // send message to start
                    Message msg = new Message();
                    msg.what = DownloadActivity.MSG_START_LOAD;
                    msg.arg1 = dms.get(pos).getSid();
                    msg.arg2 = pos;
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
            viewHolder.txtAddition.setText(context.getResources().getString(R.string.load_finish));
            viewHolder.btnStatus.setText(context.getResources().getString(R.string.watch_dm));
        } else if (!dms.get(position).isStart()) {
            viewHolder.pbStatus.setVisibility(View.GONE);
            viewHolder.txtAddition.setVisibility(View.VISIBLE);
            viewHolder.txtAddition.setText(context.getResources().getString(R.string.not_load));
            viewHolder.btnStatus.setText(context.getResources().getString(R.string.begin_load));
        } else {
            viewHolder.pbStatus.setVisibility(View.VISIBLE);
            viewHolder.txtAddition.setVisibility(View.GONE);
            viewHolder.btnStatus.setText(context.getResources().getString(R.string.loading));
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
