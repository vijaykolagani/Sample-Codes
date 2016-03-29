package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class PlaceStickerDetailsPojo implements Parcelable {
    private int success;
    private List<Events> data;

    public PlaceStickerDetailsPojo() {
        data = new ArrayList<Events>();
    }

    public PlaceStickerDetailsPojo(Parcel in) {
        this();
        success = in.readInt();
        in.readTypedList(data, Events.CREATOR);
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<Events> getData() {
        return data;
    }

    public void setData(List<Events> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(success);
        dest.writeTypedList(data);
    }

    public static final Parcelable.Creator<PlaceStickerDetailsPojo> CREATOR = new Parcelable.Creator<PlaceStickerDetailsPojo>() {

        @Override
        public PlaceStickerDetailsPojo createFromParcel(Parcel source) {
            return new PlaceStickerDetailsPojo(source);
        }

        @Override
        public PlaceStickerDetailsPojo[] newArray(int size) {
            return new PlaceStickerDetailsPojo[size];
        }
    };

    public static class Events implements Parcelable {
        private String id;
        private String name;
        private List<EventData> interest_id;
        private String location;
        private String todo;
        private String howtoreach;
        private String creat_date;
        private String creat_lat;
        private String creat_lon;
        private String creat_city;
        private String creat_full;
        private String user_id;
        private String user_fullname;
        private String user_img;
        private ArrayList<String> image;
        private String comments;
        private String comment_status;
        private String beenthere;
        private int beenthere_status;
        private String timeago;

        public Events() {
            interest_id = new ArrayList<EventData>();
            image = new ArrayList<String>();
        }

        public Events(Parcel in) {
            this();
            id = in.readString();
            name= in.readString();
            in.readTypedList(interest_id, EventData.CREATOR);
            location = in.readString();
            todo = in.readString();
            howtoreach = in.readString();
            creat_date = in.readString();
            creat_lat = in.readString();
            creat_lon = in.readString();
            creat_city = in.readString();
            creat_full = in.readString();
            user_id = in.readString();
            user_fullname = in.readString();
            user_img = in.readString();
            image = (ArrayList<String>) in.readSerializable();
            comments = in.readString();
            comment_status = in.readString();
            beenthere = in.readString();
            beenthere_status = in.readInt();
            timeago = in.readString();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<EventData> getInterest_id() {
            return interest_id;
        }

        public void setInterest_id(List<EventData> interest_id) {
            this.interest_id = interest_id;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getTodo() {
            return todo;
        }

        public void setTodo(String todo) {
            this.todo = todo;
        }

        public String getHowtoreach() {
            return howtoreach;
        }

        public void setHowtoreach(String howtoreach) {
            this.howtoreach = howtoreach;
        }

        public String getCreat_date() {
            return creat_date;
        }

        public void setCreat_date(String creat_date) {
            this.creat_date = creat_date;
        }

        public String getCreat_lat() {
            return creat_lat;
        }

        public void setCreat_lat(String creat_lat) {
            this.creat_lat = creat_lat;
        }

        public String getCreat_lon() {
            return creat_lon;
        }

        public void setCreat_lon(String creat_lon) {
            this.creat_lon = creat_lon;
        }

        public String getCreat_full() {
            return creat_full;
        }

        public void setCreat_full(String creat_full) {
            this.creat_full = creat_full;
        }

        public String getCreat_city() {
            return creat_city;
        }

        public void setCreat_city(String creat_city) {
            this.creat_city = creat_city;
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

        public ArrayList<String> getImage() {
            return image;
        }

        public void setImage(ArrayList<String> image) {
            this.image = image;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getComment_status() {
            return comment_status;
        }

        public void setComment_status(String comment_status) {
            this.comment_status = comment_status;
        }

        public String getBeenthere() {
            return beenthere;
        }

        public void setBeenthere(String beenthere) {
            this.beenthere = beenthere;
        }

        public int getBeenthere_status() {
            return beenthere_status;
        }

        public void setBeenthere_status(int beenthere_status) {
            this.beenthere_status = beenthere_status;
        }

        public String getTimeago() {
            return timeago;
        }

        public void setTimeago(String timeago) {
            this.timeago = timeago;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeTypedList(interest_id);
            dest.writeString(location);
            dest.writeString(todo);
            dest.writeString(howtoreach);
            dest.writeString(creat_date);
            dest.writeString(creat_lat);
            dest.writeString(creat_lon);
            dest.writeString(creat_city);
            dest.writeString(creat_full);
            dest.writeString(user_id);
            dest.writeString(user_fullname);
            dest.writeString(user_img);
            dest.writeSerializable(image);
            dest.writeString(comments);
            dest.writeString(comment_status);
            dest.writeString(beenthere);
            dest.writeInt(beenthere_status);
            dest.writeString(timeago);

        }

        public static final Parcelable.Creator<Events> CREATOR = new Parcelable.Creator<Events>() {

            @Override
            public Events createFromParcel(Parcel source) {
                return new Events(source);
            }

            @Override
            public Events[] newArray(int size) {
                return new Events[size];
            }
        };
    }

}
