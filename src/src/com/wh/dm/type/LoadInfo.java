
package com.wh.dm.type;

public class LoadInfo {

    private String picPath;
    private String title;
    private boolean isFinish;
    private boolean isStart;
    private boolean isPause;
    private int pro;

    public LoadInfo() {

    }

    public LoadInfo(String _picPatho, String _title, boolean isStart, boolean _isFinish,
            boolean _isPause, int _pro) {

        this.picPath = _picPatho;
        this.title = _title;
        this.isStart = isStart;
        this.isFinish = _isFinish;
        this.isPause = _isPause;
        this.pro = _pro;
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
