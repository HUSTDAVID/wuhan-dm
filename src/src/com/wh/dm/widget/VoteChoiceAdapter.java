
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.type.VoteItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class VoteChoiceAdapter extends BaseAdapter {

    ArrayList<VoteItem> votes;
    LayoutInflater inflater;
    Context context;

    public VoteChoiceAdapter(Context c) {

        inflater = LayoutInflater.from(c);
        context = c;
    }

    public void setList(ArrayList<VoteItem> voteList) {

        votes = voteList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return votes.size();
    }

    @Override
    public Object getItem(int arg0) {

        return votes.get(arg0);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.vote_item, null);
            holder.btnVoteItem = (Button) convertView.findViewById(R.id.btn_vote_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final int index = position + 1;
        holder.btnVoteItem.setText(String.valueOf(index) + "." + votes.get(position).getVotenote());

        return convertView;
    }

    private static class ViewHolder {
        Button btnVoteItem;
    }

}
