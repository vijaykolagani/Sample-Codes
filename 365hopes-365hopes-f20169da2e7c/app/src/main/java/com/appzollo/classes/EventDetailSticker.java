package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.appzollo.EventClicked;

import java.util.ArrayList;
import java.util.List;


public class EventDetailSticker implements Parcelable {
    private int success;
    private EventDetail data;

    public EventDetailSticker() {

    }

    public EventDetailSticker(Parcel in) {
        success = in.readByte();
        data = (EventDetail) in.readParcelable(EventDetail.class.getClassLoader());
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public EventDetail getData() {
        return data;
    }

    public void setData(EventDetail data) {
        this.data = data;
    }

    public static EventDetailSticker getInstance() {
        return new EventDetailSticker();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(success);
        dest.writeParcelable(data, flags);
    }

    public static final Parcelable.Creator<EventDetailSticker> CREATOR = new Parcelable.Creator<EventDetailSticker>() {

        @Override
        public EventDetailSticker createFromParcel(Parcel source) {
            return new EventDetailSticker(source);
        }

        @Override
        public EventDetailSticker[] newArray(int size) {
            return new EventDetailSticker[size];
        }
    };
}
