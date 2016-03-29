package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikrishna on 16/11/14.
 */
public class Followers implements Parcelable {
    private int success;
    private List<Data> data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Followers() {
        data = new ArrayList<Data>();
    }

    public Followers(Parcel in) {
        this();
        success = in.readByte();
        in.readTypedList(data, Data.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(success);
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
        private String friends_id;
        private String user_id;
        private String user_fullname;
        private String user_img;
        private String user_city;
        private int follows;
        private int followers;
        private int follow_status;

        public String getFriends_id() {
            return friends_id;
        }

        public void setFriends_id(String friends_id) {
            this.friends_id = friends_id;
        }

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

        public String getUser_city() {
            return user_city;
        }

        public void setUser_city(String user_city) {
            this.user_city = user_city;
        }

        public int getFollows() {
            return follows;
        }

        public void setFollows(int follows) {
            this.follows = follows;
        }

        public int getFollowers() {
            return followers;
        }

        public void setFollowers(int followers) {
            this.followers = followers;
        }

        public int getFollow_status() {
            return follow_status;
        }

        public void setFollow_status(int follow_status) {
            this.follow_status = follow_status;
        }

        public Data() {

        }

        public Data(Parcel in) {
            friends_id = in.readString();
            user_id = in.readString();
            user_fullname = in.readString();
            user_img = in.readString();
            user_city = in.readString();
            follows = in.readInt();
            followers = in.readInt();
            follow_status = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {
            dest.writeString(friends_id);
            dest.writeString(user_id);
            dest.writeString(user_fullname);
            dest.writeString(user_img);
            dest.writeString(user_city);
            dest.writeInt(follows);
            dest.writeInt(followers);
            dest.writeInt(follow_status);
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
