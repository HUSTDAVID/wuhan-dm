
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.type.PostMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
    public View getView(int position, View convertView, ViewGroup parent) {

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

        return convertView;
    }

    private static class ViewHolder {
        TextView txtLarge;
        TextView txtSmall;
    }

}
