
package com.wh.dm.type;

public class LoadInfo {

    private int sid;
    private String picPath;
    private String title;
    private boolean isFinish;
    private boolean isStart;
    private boolean isPause;
    private int pro;
    private boolean ok = false;

    public boolean isOk() {

        return ok;
    }

    public void setOk(boolean ok) {

        this.ok = ok;
    }

    public LoadInfo() {

    }

    public int getSid() {

        return sid;
    }

    public void setSid(int sid) {

        this.sid = sid;
    }

    public boolean isStart() {

        return isStart;
    }

    public void setStart(boolean isStart) {

        this.isStart = isStart;
    }

    public int getPro() {

        return pro;
    }

    public void setPro(int pro) {

        this.pro = pro;
    }

    public String getPicPath() {

        return picPath;
    }

    public void setPicPath(String picPath) {

        this.picPath = picPath;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public boolean isFinish() {

        return isFinish;
    }

    public void setFinish(boolean isFinish) {

        this.isFinish = isFinish;
    }

    public boolean isPause() {

        return isPause;
    }

    public void setPause(boolean isPause) {

        this.isPause = isPause;
    }

}
