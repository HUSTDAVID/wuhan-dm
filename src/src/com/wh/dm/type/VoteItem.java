
package com.wh.dm.type;

public class VoteItem {

    private int no;
    private int id;
    private int aid;
    private String used_ips;
    private String voteaddtime;
    private String votenote;

    public String getVotenote() {

        return votenote;
    }

    public void setVotenote(String votenote) {

        this.votenote = votenote;
    }

    public int getNo() {

        return no;
    }

    public void setNo(int no) {

        this.no = no;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getAid() {

        return aid;
    }

    public void setAid(int aid) {

        this.aid = aid;
    }

    public String getUsed_ips() {

        return used_ips;
    }

    public void setUsed_ips(String used_ips) {

        this.used_ips = used_ips;
    }

    public String getVoteaddtime() {

        return voteaddtime;
    }

    public void setVoteaddtime(String voteaddtime) {

        this.voteaddtime = voteaddtime;
    }

}
