package com.appzollo.classes;

/**
 * Created by prasad on 11/9/2014.
 */
public class RowItemInterest {
    String name;
    String img;
    boolean check;

    public RowItemInterest(String name, String img, boolean check) {
        this.name = name;
        this.img = img;
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
