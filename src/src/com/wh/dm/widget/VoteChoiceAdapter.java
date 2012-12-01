
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class VoteChoiceAdapter extends BaseAdapter {

    // ArrayList<VoteItem> votes;
    String[] votes;
    boolean[] select;
    LayoutInflater inflater;
    Context context;

    public VoteChoiceAdapter(Context c) {

        inflater = LayoutInflater.from(c);
        context = c;
    }

    public void setList(String[] voteList) {

        votes = voteList;
        notifyDataSetChanged();
    }

    public void setSelect(boolean[] select) {

        this.select = select;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return votes.length;
    }

    @Override
    public Object getItem(int arg0) {

        return votes[arg0];
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
        holder.btnVoteItem.setText(String.valueOf(index) + "." + votes[position]);
        if (select != null && select.length > position) {
            holder.btnVoteItem.setSelected(select[position]);
        }

        return convertView;
    }

    private static class ViewHolder {
        Button btnVoteItem;
    }

}
