package com.appzollo.classes;

/**
 * Created by prasad on 11/8/2014.
 */
public class RowItemPlace{
String title;
String palce;

    public RowItemPlace(String title, String palce, String distance) {
        this.title = title;
        this.palce = palce;
        this.distance = distance;
    }

    public RowItemPlace(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPalce() {
        return palce;
    }

    public void setPalce(String palce) {
        this.palce = palce;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    String distance;



}
