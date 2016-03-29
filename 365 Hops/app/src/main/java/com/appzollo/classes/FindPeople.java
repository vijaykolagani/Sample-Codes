package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikrishna on 16/11/14.
 */
public class FindPeople implements Parcelable {
    private int status;
    private List<Data> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public FindPeople() {
        data = new ArrayList<Data>();
    }

    public FindPeople(Parcel in) {
        this();
        status = in.readByte();
        in.readTypedList(data, Data.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(status);
        dest.writeTypedList(data);
    }

    public static final Parcelable.Creator<Comments> CREATOR = new Parcelable.Creator<Comments>() {

        @Override
        public Comments createFromParcel(Parcel source) {
            return new Comments(source);
        }

        @Override
        public Comments[] newArray(int size) {
            return new Comments[size];
        }
    };

    public static class Data implements Parcelable {

        private String user_id;
        private String user_fullname;
        private String user_img;
        private String follows;
        private String followers;


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_fullname() {
            return user_fullname;
        }

        public void setUser_fullname(String user_fullname) {
            this.user_fullname = user_fullname;
        }

        public String getUser_img() {
            return user_img;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public String getFollows() {
            return follows;
        }

        public void setFollows(String follows) {
            this.follows = follows;
        }

        public String getFollowers() {
            return followers;
        }

        public void setFollowers(String followers) {
            this.followers = followers;
        }

        public Data() {

        }

        public Data(Parcel in) {
            user_id = in.readString();
            user_fullname = in.readString();
            user_img = in.readString();
            follows = in.readString();
            followers = in.readString();

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {
            dest.writeString(user_id);
            dest.writeString(user_fullname);
            dest.writeString(user_img);
            dest.writeString(follows);
            dest.writeString(followers);

        }

        public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {

            @Override
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

    }
}
