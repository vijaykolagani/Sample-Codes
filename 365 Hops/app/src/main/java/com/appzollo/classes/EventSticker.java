package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prasad on 10/31/2014.
 */
public class EventSticker implements Parcelable {
    private int success;
    private List<Events> data;

    public EventSticker() {
        data = new ArrayList<Events>();
    }

    public EventSticker(Parcel in) {
        this();
        success = in.readByte();
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

    public static EventSticker getInstance() {
        return new EventSticker();
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

    public static final Parcelable.Creator<EventSticker> CREATOR = new Parcelable.Creator<EventSticker>() {

        @Override
        public EventSticker createFromParcel(Parcel source) {
            return new EventSticker(source);
        }

        @Override
        public EventSticker[] newArray(int size) {
            return new EventSticker[size];
        }
    };

    public static class Events implements Parcelable {


        private String id;
        private String title;
        private String where_area;
        private String date_from;
        private String date_to;
        private String custome_date;
        private String date;
        private String cost;
        private String duration;
        private String istravel;
        private String isfood;
        private String isaccommodation;
        private String latitude_creat;
        private String longitude_creat;
        private String city_creat;
        private String user_id;
        private String user_fullname;
        private String user_img;
        private String image;
        private String distance;
        private String timeago;
        private String comments;
        private String comments_status;
        private String intrests;
        private String intrest_status;
        private String type;

        public Events() {

        }

        public Events(Parcel in) {
            id = in.readString();
            title = in.readString();
            where_area = in.readString();
            date_from = in.readString();
            date_to = in.readString();
            custome_date = in.readString();
            date = in.readString();
            cost = in.readString();
            duration = in.readString();
            istravel = in.readString();
            isfood = in.readString();
            isaccommodation = in.readString();
            latitude_creat = in.readString();
            longitude_creat = in.readString();
            city_creat = in.readString();
            user_id = in.readString();
            user_fullname = in.readString();
            user_img = in.readString();
            image = in.readString();
            distance = in.readString();
            timeago = in.readString();
            comments = in.readString();
            comments_status = in.readString();
            intrests = in.readString();
            intrest_status = in.readString();
            type = in.readString();
        }


        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getWhere_area() {
            return where_area;
        }

        public String getDate_from() {
            return date_from;
        }

        public String getDate_to() {
            return date_to;
        }

        public String getCustomeDate() {
            return custome_date;
        }

        public String getDate() {
            return date;
        }

        public String getCost() {
            return cost;
        }

        public String getIstravel() {
            return istravel;
        }

        public String getIsfood() {
            return isfood;
        }

        public String getIsaccommodation() {
            return isaccommodation;
        }

        public String getLatitudeCreat() {
            return latitude_creat;
        }

        public String getLongitudeCreat() {
            return longitude_creat;
        }

        public String getCityCreat() {
            return city_creat;
        }

        public String getUserId() {
            return user_id;
        }

        public String getUser_fullname() {
            return user_fullname;
        }

        public String getUser_Img() {
            return user_img;
        }

        public String getImage() {
            return image;
        }

        public String getDistance() {
            return distance;
        }

        public String getTimeago() {
            return timeago;
        }

        public String getComments() {
            return comments;
        }

        public String getCommentsStatus() {
            return comments_status;
        }

        public String getIntrests() {
            return intrests;
        }

        public String getIntrestStatus() {
            return intrest_status;
        }

        public String getType() {
            return type;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setWhere_area(String where_area) {
            this.where_area = where_area;
        }

        public void setDate_from(String date_from) {
            this.date_from = date_from;
        }

        public void setDate_to(String date_to) {
            this.date_to = date_to;
        }

        public void setCustomeDate(String customeDate) {
            this.custome_date = customeDate;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public void setIstravel(String istravel) {
            this.istravel = istravel;
        }

        public void setIsfood(String isfood) {
            this.isfood = isfood;
        }

        public void setIsaccommodation(String isaccommodation) {
            this.isaccommodation = isaccommodation;
        }

        public void setLatitudeCreat(String latitude_creat) {
            this.latitude_creat = latitude_creat;
        }

        public void setLongitudeCreat(String longitudeCreat) {
            this.longitude_creat = longitudeCreat;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setCityCreat(String cityCreat) {
            this.city_creat = cityCreat;
        }

        public void setUserId(String userId) {
            this.user_id = userId;
        }

        public void setUser_fullname(String user_fullname) {
            this.user_fullname = user_fullname;
        }

        public void setUser_Img(String userImg) {
            this.user_img = userImg;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public void setTimeago(String timeago) {
            this.timeago = timeago;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public void setCommentsStatus(String commentsStatus) {
            this.comments_status = commentsStatus;
        }

        public void setIntrests(String intrests) {
            this.intrests = intrests;
        }

        public void setIntrestStatus(String intrestStatus) {
            this.intrest_status = intrestStatus;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(title);
            dest.writeString(where_area);
            dest.writeString(date_from);
            dest.writeString(date_to);
            dest.writeString(custome_date);
            dest.writeString(date);
            dest.writeString(cost);
            dest.writeString(duration);
            dest.writeString(istravel);
            dest.writeString(isfood);
            dest.writeString(isaccommodation);
            dest.writeString(latitude_creat);
            dest.writeString(longitude_creat);
            dest.writeString(city_creat);
            dest.writeString(user_id);
            dest.writeString(user_fullname);
            dest.writeString(user_img);
            dest.writeString(image);
            dest.writeString(distance);
            dest.writeString(timeago);
            dest.writeString(comments );
            dest.writeString(comments_status);
            dest.writeString(intrests);
            dest.writeString(intrest_status);
            dest.writeString(type);
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

        public static Events getInstance() {
            return new Events();
        }
    }


}
