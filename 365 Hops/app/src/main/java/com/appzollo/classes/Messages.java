package com.appzollo.classes;

/**
 * Created by prasad on 11/17/2014.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by saikrishna on 16/11/14.
 */
public class Messages implements Parcelable {

    private List<Data> data;


    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Messages() {
        data = new ArrayList<Data>();
    }

    public Messages(Parcel in) {
        this();

        in.readTypedList(data, Data.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {

        dest.writeTypedList(data);
    }

    public static final Parcelable.Creator<Messages> CREATOR = new Parcelable.Creator<Messages>() {

        @Override
        public Messages createFromParcel(Parcel source) {
            return new Messages(source);
        }

        @Override
        public Messages[] newArray(int size) {
            return new Messages[size];
        }
    };

    public static class Data implements Parcelable {
        private String message_id;
        private String sender_id;
        private String reciever_id;
        private String message;
        private String send_date;
        private String sendtime;
        private String message_status;
        private String sender_name;
        private String sender_img;
        private String reciever_name;
        private String reciever_img;

        public String getMessage_id() {
            return message_id;
        }

        public void setMessage_id(String message_id) {
            this.message_id = message_id;
        }

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public String getReciever_id() {
            return reciever_id;
        }

        public void setReciever_id(String reciever_id) {
            this.reciever_id = reciever_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSend_date() {
            return send_date;
        }

        public void setSend_date(String send_date) {
            this.send_date = send_date;
        }

        public String getSendtime() {
            return sendtime;
        }

        public void setSendtime(String sendtime) {
            this.sendtime = sendtime;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
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

        public String getReciever_name() {
            return reciever_name;
        }

        public void setReciever_name(String reciever_name) {
            this.reciever_name = reciever_name;
        }

        public String getReciever_img() {
            return reciever_img;
        }

        public void setReciever_img(String reciever_img) {
            this.reciever_img = reciever_img;
        }

        public Data() {

        }

        public Data(Parcel in) {
            message_id = in.readString();
            sender_id = in.readString();
            reciever_id = in.readString();
            message = in.readString();
            send_date = in.readString();
            sendtime = in.readString();
            message_status = in.readString();
            sender_name = in.readString();
            sender_img = in.readString();
            reciever_name = in.readString();
            reciever_img = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {
            dest.writeString(message_id);
            dest.writeString(sender_id);
            dest.writeString(reciever_id);
            dest.writeString(message);
            dest.writeString(send_date);
            dest.writeString(sendtime);
            dest.writeString(message_status);
            dest.writeString(sender_name);
            dest.writeString(sender_img);
            dest.writeString(reciever_name);
            dest.writeString(reciever_img);
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

