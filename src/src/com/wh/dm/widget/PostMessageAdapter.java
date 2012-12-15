
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.activity.DM_Tab_2Activity;
import com.wh.dm.type.PostMessage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PostMessageAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<PostMessage> messages;

    public PostMessageAdapter(Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<PostMessage> list) {

        this.messages = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        if (messages != null) {
            return messages.size();
        }
        return 0;

    }

    @Override
    public Object getItem(int position) {

        if (messages != null) {
            return messages.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {

        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.message_item, null);
            holder.txtLarge = (TextView) convertView.findViewById(R.id.tex_message_large);
            holder.txtSmall = (TextView) convertView.findViewById(R.id.tex_message_small);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtLarge.setText(messages.get(position).getTitle());
        holder.txtSmall.setText(messages.get(position).getMname());

        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent_magazine = new Intent(context, DM_Tab_2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("dm", messages.get(position).getTemp());
                bundle.putInt("sid", messages.get(position).getMid());
                intent_magazine.putExtras(bundle);
                context.startActivity(intent_magazine);

            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView txtLarge;
        TextView txtSmall;
    }

}
