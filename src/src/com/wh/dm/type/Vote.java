
package com.wh.dm.type;

public class Vote {

    private int no;
    private int aid;
    private String votename;
    private String endtime;
    private String starttime;
    private boolean ismore;
    private String[] votenote;
    private boolean isenable;

    public int getNo() {

        return no;
    }

    public void setNo(int no) {

        this.no = no;
    }

    public int getAid() {

        return aid;
    }

    public void setAid(int aid) {

        this.aid = aid;
    }

    public String getVotename() {

        return votename;
    }

    public void setVotename(String votename) {

        this.votename = votename;
    }

    public String getEndtime() {

        return endtime;
    }

    public void setEndtime(String endtime) {

        this.endtime = endtime;
    }

    public String getStarttime() {

        return starttime;
    }

    public void setStarttime(String starttime) {

        this.starttime = starttime;
    }

    public boolean isIsmore() {

        return ismore;
    }

    public void setIsmore(boolean ismore) {

        this.ismore = ismore;
    }

    public String[] getVotenote() {

        return votenote;
    }

    public void setVotenote(String[] votenote) {

        this.votenote = votenote;
    }

    public boolean isIsenable() {

        return isenable;
    }

    public void setIsenable(boolean isenable) {

        this.isenable = isenable;
    }

}
