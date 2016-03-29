package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yogasaikrishna on 09-12-2014.
 */
public class EventData implements Parcelable {
    private String id;
    private String icon_small;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon_small() {
        return icon_small;
    }

    public void setIcon_small(String icon_small) {
        this.icon_small = icon_small;
    }

    public EventData() {

    }

    public EventData(Parcel in) {
        id = in.readString();
        icon_small = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(id);
        dest.writeString(icon_small);

    }

    public static final Parcelable.Creator<EventData> CREATOR = new Parcelable.Creator<EventData>() {

        @Override
        public EventData createFromParcel(Parcel source) {
            return new EventData(source);
        }

        @Override
        public EventData[] newArray(int size) {
            return new EventData[size];
        }
    };

}