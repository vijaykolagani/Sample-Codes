package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikrishna on 16/11/14.
 */
public class Enquiry implements Parcelable {
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

    public Enquiry() {
        data = new ArrayList<Data>();
    }

    public Enquiry(Parcel in) {
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

    public static final Parcelable.Creator<Enquiry> CREATOR = new Parcelable.Creator<Enquiry>() {

        @Override
        public Enquiry createFromParcel(Parcel source) {
            return new Enquiry(source);
        }

        @Override
        public Enquiry[] newArray(int size) {
            return new Enquiry[size];
        }
    };

    public static class Data implements Parcelable {
        private String id;
        private String eventid;
        private String mobile;
        private String date;
        private String name;
        private String email;
        private String peoples;
        private String activityname;
        private String userid;
        private String userimage;
        private String activitytype;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEventid() {
            return eventid;
        }

        public void setEventid(String eventid) {
            this.eventid = eventid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPeoples() {
            return peoples;
        }

        public void setPeoples(String peoples) {
            this.peoples = peoples;
        }

        public String getActivityname() {
            return activityname;
        }

        public void setActivityname(String activityname) {
            this.activityname = activityname;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUserimage() {
            return userimage;
        }

        public void setUserimage(String userimage) {
            this.userimage = userimage;
        }

        public String getActivitytype() {
            return activitytype;
        }

        public void setActivitytype(String activitytype) {
            this.activitytype = activitytype;
        }

        public Data() {

        }

        public Data(Parcel in) {
            id = in.readString();
            eventid = in.readString();
            mobile = in.readString();
            date = in.readString();
            name = in.readString();
            email = in.readString();
            peoples = in.readString();
            activityname = in.readString();
            userid = in.readString();
            userimage = in.readString();
            activitytype = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {
            dest.writeString(id);
            dest.writeString(eventid);
            dest.writeString(mobile);
            dest.writeString(date);
            dest.writeString(name);
            dest.writeString(email);
            dest.writeString(peoples);
            dest.writeString(activityname);
            dest.writeString(userid);
            dest.writeString(userimage);
            dest.writeString(activitytype);
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
