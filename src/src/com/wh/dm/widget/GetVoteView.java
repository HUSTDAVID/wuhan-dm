
package com.wh.dm.widget;

import com.wh.dm.R;
import com.wh.dm.type.Vote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetVoteView {

    public static Context context;

    public static View getView(Vote vote, Context c) {

        context = c;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dm_voteitem1, null);

        ImageView img = (ImageView) view.findViewById(R.id.vote_imageView1);
        Button btnTitle = (Button) view.findViewById(R.id.btn_votelist);
        TextView txtQuestion = (TextView) view.findViewById(R.id.vote_list_1);
        Button btnVote = (Button) view.findViewById(R.id.btn_vote);
        TextView txtIsEnable = (TextView) view.findViewById(R.id.vote_ing_3);
        TextView txtTime = (TextView) view.findViewById(R.id.vote_ing_4);

        txtQuestion.setText(vote.getVotename());
        if (vote.isIsenable()) {
            txtIsEnable.setText(context.getResources().getString(R.string.voting));
        } else {
            txtIsEnable.setText(context.getResources().getString(R.string.voted));
        }
        txtTime.setText(vote.getStarttime());

        return view;
    }

}
