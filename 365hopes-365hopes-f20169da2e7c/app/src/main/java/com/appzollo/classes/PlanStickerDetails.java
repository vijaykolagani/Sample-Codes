package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class PlanStickerDetails implements Parcelable {
    private int success;
    private Events data;

    public PlanStickerDetails() {

    }

    public PlanStickerDetails(Parcel in) {
        success = in.readByte();
        data = (Events) in.readParcelable(Events.class.getClassLoader());
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public Events getData() {
        return data;
    }

    public void setData(Events data) {
        this.data = data;
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

    public static final Parcelable.Creator<PlanStickerDetails> CREATOR = new Parcelable.Creator<PlanStickerDetails>() {

        @Override
        public PlanStickerDetails createFromParcel(Parcel source) {
            return new PlanStickerDetails(source);
        }

        @Override
        public PlanStickerDetails[] newArray(int size) {
            return new PlanStickerDetails[size];
        }
    };

    public static class Events implements Parcelable {
        private String id;
        private List<EventData> segmentid;
        private String title;
        private String image;
        private String date_from;
        private String date_to;
        private String creation_date;
        private String where_area;
        private String latitude_creat;
        private String longitude_creat;
        private String city_creat;
        private  String creat_full;
        private String user_id;
        private String user_fullname;
        private String user_img;
        private String timeago;
        private String comments;
        private String comments_status;
        private int intrests;
        private int intrest_status;

        public Events() {
            segmentid = new ArrayList<EventData>();
        }

        public Events(Parcel in) {
            this();
            id = in.readString();
            in.readTypedList(segmentid, EventData.CREATOR);
            title = in.readString();
            image = in.readString();
            date_from = in.readString();
            date_to = in.readString();
            creation_date = in.readString();
            where_area = in.readString();
            latitude_creat = in.readString();
            longitude_creat = in.readString();
            city_creat = in.readString();
            creat_full = in.readString();
            user_id = in.readString();
            user_fullname = in.readString();
            user_img = in.readString();
            timeago = in.readString();
            comments = in.readString();
            comments_status = in.readString();
            intrests = in.readInt();
            intrest_status = in.readInt();
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<EventData> getSegmentid() {
            return segmentid;
        }

        public void setSegmentid(List<EventData> segmentid) {
            this.segmentid = segmentid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDate_from() {
            return date_from;
        }

        public void setDate_from(String date_from) {
            this.date_from = date_from;
        }

        public String getDate_to() {
            return date_to;
        }

        public void setDate_to(String date_to) {
            this.date_to = date_to;
        }

        public String getCreation_date() {
            return creation_date;
        }

        public void setCreation_date(String creation_date) {
            this.creation_date = creation_date;
        }

        public String getWhere_area() {
            return where_area;
        }

        public void setWhere_area(String where_area) {
            this.where_area = where_area;
        }

        public String getLatitude_creat() {
            return latitude_creat;
        }

        public void setLatitude_creat(String latitude_creat) {
            this.latitude_creat = latitude_creat;
        }

        public String getLongitude_creat() {
            return longitude_creat;
        }

        public void setLongitude_creat(String longitude_creat) {
            this.longitude_creat = longitude_creat;
        }

        public String getCity_creat() {
            return city_creat;
        }

        public void setCity_creat(String city_creat) {
            this.city_creat = city_creat;
        }

        public String getCreat_full() {
            return creat_full;
        }

        public void setCreat_full(String creat_full) {
            this.creat_full = creat_full;
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

        public String getTimeago() {
            return timeago;
        }

        public void setTimeago(String timeago) {
            this.timeago = timeago;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getComments_status() {
            return comments_status;
        }

        public void setComments_status(String comments_status) {
            this.comments_status = comments_status;
        }

        public int getIntrests() {
            return intrests;
        }

        public void setIntrests(int intrests) {
            this.intrests = intrests;
        }

        public int getIntrest_status() {
            return intrest_status;
        }

        public void setIntrest_status(int intrest_status) {
            this.intrest_status = intrest_status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeTypedList(segmentid);
            dest.writeString(title);
            dest.writeString(image);
            dest.writeString(date_from);
            dest.writeString(date_to);
            dest.writeString(creation_date);
            dest.writeString(where_area);
            dest.writeString(latitude_creat);
            dest.writeString(longitude_creat);
            dest.writeString(city_creat);
            dest.writeString(creat_full);
            dest.writeString(user_id);
            dest.writeString(user_fullname);
            dest.writeString(user_img);
            dest.writeString(timeago);
            dest.writeString(comments);
            dest.writeString(comments_status);
            dest.writeInt(intrests);
            dest.writeInt(intrest_status);
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
