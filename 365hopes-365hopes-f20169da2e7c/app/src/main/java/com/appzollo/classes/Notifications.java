package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikrishna on 17/11/14.
 */
public class Notifications implements Parcelable {
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

    public Notifications() {
        data = new ArrayList<Data>();
    }

    public Notifications(Parcel in) {
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
        private String userid;
        private String postedby;
        private String message;
        private String postid;
        private String posttype;
        private String ppostid;
        private String ppostname;
        private String time;
        private String status;
        private String sender_id;
        private String sender_name;
        private String sender_img;
        private String redirectid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPostedby() {
            return postedby;
        }

        public void setPostedby(String postedby) {
            this.postedby = postedby;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public String getPosttype() {
            return posttype;
        }

        public void setPosttype(String posttype) {
            this.posttype = posttype;
        }

        public String getPpostid() {
            return ppostid;
        }

        public void setPpostid(String ppostid) {
            this.ppostid = ppostid;
        }

        public String getPpostname() {
            return ppostname;
        }

        public void setPpostname(String ppostname) {
            this.ppostname = ppostname;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public String getSender_name() {
            return sender_name;
        }

        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public String getSender_img() {
            return sender_img;
        }

        public void setSender_img(String sender_img) {
            this.sender_img = sender_img;
        }
        public String getRedirectid() {
            return redirectid;
        }

        public void setRedirectid(String redirectid) {
            this.redirectid = redirectid;
        }

        public Data() {

        }

        public Data(Parcel in) {
            id = in.readString();
            userid = in.readString();
            postedby = in.readString();
            message = in.readString();
            postid = in.readString();
            posttype = in.readString();
            ppostid = in.readString();
            ppostname = in.readString();
            time = in.readString();
            status = in.readString();
            sender_id = in.readString();
            sender_name = in.readString();
            sender_img = in.readString();
            redirectid = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {
            dest.writeString(id);
            dest.writeString(userid);
            dest.writeString(postedby);
            dest.writeString(message);
            dest.writeString(postid);
            dest.writeString(posttype);
            dest.writeString(ppostid);
            dest.writeString(ppostname);
            dest.writeString(time);
            dest.writeString(status);
            dest.writeString(sender_id);
            dest.writeString(sender_name);
            dest.writeString(sender_img);
            dest.writeString(redirectid);

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
