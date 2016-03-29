package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikrishna on 18/11/14.
 */
public class Events implements Parcelable {
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

    public Events() {
        data = new ArrayList<Data>();
    }

    public Events(Parcel in) {
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
        private String cost;
        private String where_area;
        private String fulladdress_name;
        private String plc_name;
        private String plc_name2;
        private String plc_name3;
        private String image;
        private String type;

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

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getWhere_area() {
            return where_area;
        }

        public void setWhere_area(String where_area) {
            this.where_area = where_area;
        }

        public String getFulladdress_name() {
            return fulladdress_name;
        }

        public void setFulladdress_name(String fulladdress_name) {
            this.fulladdress_name = fulladdress_name;
        }

        public String getPlc_name() {
            return plc_name;
        }

        public void setPlc_name(String plc_name) {
            this.plc_name = plc_name;
        }

        public String getPlc_name2() {
            return plc_name2;
        }

        public void setPlc_name2(String plc_name2) {
            this.plc_name2 = plc_name2;
        }

        public String getPlc_name3() {
            return plc_name3;
        }

        public void setPlc_name3(String plc_name3) {
            this.plc_name3 = plc_name3;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Data() {

        }

        public Data(Parcel in) {
            id = in.readString();
            name = in.readString();
            cost = in.readString();
            where_area = in.readString();
            fulladdress_name = in.readString();
            plc_name = in.readString();
            plc_name2 = in.readString();
            plc_name3 = in.readString();
            image = in.readString();
            type = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(cost);
            dest.writeString(where_area);
            dest.writeString(fulladdress_name);
            dest.writeString(plc_name);
            dest.writeString(plc_name2);
            dest.writeString(plc_name3);
            dest.writeString(image);
            dest.writeString(type);
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
