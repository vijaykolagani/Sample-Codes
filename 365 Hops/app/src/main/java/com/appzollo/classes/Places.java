package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikrishna on 18/11/14.
 */
public class Places implements Parcelable {
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

    public Places() {
        data = new ArrayList<Data>();
    }

    public Places(Parcel in) {
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
        private String id;
        private String name;
        private String location;
        private String image;
        private String price;
        private String currency;
        private String room_type;
        private String stay_type;
        private String data_type;

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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getRoom_type() {
            return room_type;
        }

        public void setRoom_type(String room_type) {
            this.room_type = room_type;
        }

        public String getStay_type() {
            return stay_type;
        }

        public void setStay_type(String stay_type) {
            this.stay_type = stay_type;
        }

        public String getData_type() {
            return data_type;
        }

        public void setData_type(String data_type) {
            this.data_type = data_type;
        }

        public Data() {

        }

        public Data(Parcel in) {
            id = in.readString();
            name = in.readString();
            location = in.readString();
            image = in.readString();
            price = in.readString();
            currency = in.readString();
            room_type = in.readString();
            stay_type = in.readString();
            data_type = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(location);
            dest.writeString(image);
            dest.writeString(price);
            dest.writeString(currency);
            dest.writeString(room_type);
            dest.writeString(stay_type);
            dest.writeString(data_type);
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
