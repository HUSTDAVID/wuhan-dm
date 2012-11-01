
package com.wh.dm.type;

import java.util.ArrayList;

public class Review {
    private Comment comment;
    private ArrayList<Reply> reply;

    public Review() {

        comment = new Comment();
        reply = new ArrayList<Reply>();
    }

    public Comment getComment() {

        return comment;
    }

    public void setComment(Comment _comment) {

        comment = _comment;
    }

    public ArrayList<Reply> getReply() {

        return reply;
    }

    public void setReply(ArrayList<Reply> _reply) {

        reply = _reply;
    }

}
