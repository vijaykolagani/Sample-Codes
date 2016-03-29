package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by Prasad on 04-Nov-14.
 */
public class PlacestickerDetails implements Parcelable {

    private int success;
    private List<PlaceDetails> data;

    public PlacestickerDetails() {
        data = new ArrayList<PlaceDetails>();
    }

    public PlacestickerDetails(Parcel in) {
        this();
        success = in.readByte();
        in.readTypedList(data, PlaceDetails.CREATOR);
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<PlaceDetails> getData() {
        return data;
    }

    public void setData(List<PlaceDetails> data) {
        this.data = data;
    }

    public static PlacestickerDetails getInstance() {
        return new PlacestickerDetails();
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

    public static final Parcelable.Creator<PlacestickerDetails> CREATOR = new Parcelable.Creator<PlacestickerDetails>() {

        @Override
        public PlacestickerDetails createFromParcel(Parcel source) {
            return new PlacestickerDetails(source);
        }

        @Override
        public PlacestickerDetails[] newArray(int size) {
            return new PlacestickerDetails[size];
        }
    };

    public static class PlaceDetails implements Parcelable {


        private String id;
        private String name;
        private String location;
        // private List<Segment> segment;
        private String howtoreach;
        private String creat_date;
        private String creat_lat;
        private String creat_lon;
        private String creat_city;
        private String user_id;
        private String user_fullname;
        private String user_img;
        private String image;
        private String distance;
        private String comments;
        private  String comment_status;
        private String beenthere;
        private String beenthere_status;
        private String timeago;
        private  String price;
        private String currency;
        private String stay_type;
        private String room_type;
        private String food_availability;
        private String contactno;
        private String data_type;


        public PlaceDetails() {

        }

        public PlaceDetails(Parcel in) {
            id = in.readString();
            name = in.readString();
            location = in.readString();
            howtoreach = in.readString();
            creat_date = in.readString();
            creat_lat = in.readString();
            creat_lon = in.readString();
            creat_city = in.readString();
            user_id = in.readString();
            user_fullname = in.readString();
            user_img = in.readString();
            image = in.readString();
            distance = in.readString();
            comments = in.readString();
            comment_status = in.readString();
            beenthere = in.readString();
            beenthere_status = in.readString();
            timeago = in.readString();
            price = in.readString();
            currency = in.readString();
            stay_type = in.readString();
            room_type = in.readString();
            food_availability = in.readString();
            contactno = in.readString();
            data_type = in.readString();

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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }



        public String getHowToReach() {
            return howtoreach;
        }

        public void setHowToReach(String howtoreach) {
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

        public void setCreate_lat(String creat_lat) {
            this.creat_lat = creat_lat;
        }

        public String getCreat_lon() {
            return creat_lon;
        }

        public void setCreat_lon(String date) {
            this.creat_lon = creat_lon;
        }

        public String getCreat_city() {
            return creat_city;
        }

        public void setCreat_city(String creat_city) {
            this.creat_city = creat_city;
        }

        public String getUserid() {
            return user_id;
        }

        public void setUserid(String user_id) {
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
        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }



        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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

        public String getBeenthere_status() {
            return beenthere_status;
        }

        public void setBeenthere_status(String beenthere_status) {
            this.beenthere_status = beenthere_status;
        }

        public String getTimeago() {
            return timeago;
        }

        public void setTimeago(String timeago) {
            this.timeago = timeago;
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

        public String getStay_type() {
            return stay_type;
        }

        public void setStay_type(String stay_type) {
            this.stay_type = stay_type;
        }

        public String getRoom_type() {
            return room_type;
        }

        public void setRoom_type(String room_type) {
            this.room_type = room_type;
        }

        public String getFood_availability() {
            return food_availability;
        }

        public void setFood_availability(String food_availability) {
            this.food_availability = food_availability;
        }

        public String getContactno() {
            return contactno;
        }

        public void setContactno(String contactno) {
            this.contactno = contactno;
        }

        public String getData_type() {
            return data_type;
        }

        public void setData_type(String data_type) {
            this.data_type = data_type;
        }



        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(location);
            dest.writeString(howtoreach);
            dest.writeString(creat_date);

            dest.writeString(creat_lat);
            dest.writeString(creat_lon);
            dest.writeString(creat_city);
            dest.writeString(user_id);
            dest.writeString(user_fullname);
            dest.writeString(user_img);
            dest.writeString(image);
            dest.writeString(distance);
            dest.writeString(comments);
            dest.writeString(comment_status);
            dest.writeString(beenthere);
            dest.writeString(beenthere_status);
            dest.writeString(timeago);
            dest.writeString(price);
            dest.writeString(currency);
            dest.writeString(stay_type);
            dest.writeString(room_type);
            dest.writeString(food_availability);
            dest.writeString(contactno);
            dest.writeString(data_type);


        }

        public static final Parcelable.Creator<PlaceDetails> CREATOR = new Parcelable.Creator<PlaceDetails>() {

            @Override
            public PlaceDetails createFromParcel(Parcel source) {
                return new PlaceDetails(source);
            }

            @Override
            public PlaceDetails[] newArray(int size) {
                return new PlaceDetails[size];
            }
        };

        public static PlaceDetails getInstance() {
            return new PlaceDetails();
        }
    }
    public static class Segment implements Parcelable {


        private static String id;
        private String icon_small;


        public Segment() {

        }

        public Segment(Parcel in) {
            id = in.readString();
            icon_small = in.readString();

        }

        public static String getId() {
            return id;
        }

        public static void setId(String id) {
            Segment.id = id;
        }

        public String getIcon_small() {
            return icon_small;
        }

        public void setIcon_small(String icon_small) {
            this.icon_small = icon_small;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(icon_small);

        }

        public static final Parcelable.Creator<Segment> CREATOR = new Parcelable.Creator<Segment>() {

            @Override
            public Segment createFromParcel(Parcel source) {
                return new Segment(source);
            }

            @Override
            public Segment[] newArray(int size) {
                return new Segment[size];
            }
        };

        public static Segment getInstance() {
            return new Segment();
        }
    }
}
