
package com.wh.dm.type;

public class DM {

    private String dm;
    private int id = 0;
    private boolean isRed = false;

    public String getDM() {

        return dm;
    }

    public void setDM(String dm) {

        this.dm = dm;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setRed(boolean color) {

        isRed = color;
    }

    public boolean getRed() {

        return isRed;
    }
}
