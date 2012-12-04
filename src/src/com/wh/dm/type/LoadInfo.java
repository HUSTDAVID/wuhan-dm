
package com.wh.dm.type;

import android.graphics.Bitmap;

public class LoadInfo {

    Bitmap bmpLogo;
    private String title;
    private String num;
    private boolean isFinish;
    private boolean isStart;
    private boolean isPause;
    private int pro;

    public LoadInfo() {

    }

    public LoadInfo(Bitmap _bmpLogo, String _title, String _num, boolean isStart,
            boolean _isFinish, boolean _isPause, int _pro) {

        this.bmpLogo = _bmpLogo;
        this.title = _title;
        this.num = _num;
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

    public Bitmap getBmpLogo() {

        return bmpLogo;
    }

    public void setBmpLogo(Bitmap bmpLogo) {

        this.bmpLogo = bmpLogo;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getNum() {

        return num;
    }

    public void setNum(String num) {

        this.num = num;
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
