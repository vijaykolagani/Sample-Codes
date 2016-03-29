package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Prasad on 04-Nov-14.
 */
public class UserProfile implements Parcelable{
    private int success;
    private Data data;

    public UserProfile() {
        data = new Data();
    }

    public UserProfile(Parcel in) {
        this();
        success = in.readByte();
        data =(Data) in.readParcelable(Data.class.getClassLoader());
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static UserProfile getInstance() {
        return new UserProfile();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(success);
        dest.writeParcelable(data,flags);
    }

    public static final Parcelable.Creator<UserProfile> CREATOR = new Parcelable.Creator<UserProfile>() {

        @Override
        public UserProfile createFromParcel(Parcel source) {
            return new UserProfile(source);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    public static class Data implements Parcelable{

        private String user_id;
        private String user_email;
        private String user_fb_id;
        private String loginsource;
        private String usertype;
        private String serviceid;
        private String user_dob;
        private String user_fullname;
        private String user_img;
        private String user_sex;
        private String userregisterdate;
        private String user_city;
        private String location1;
        private String location2;
        private String location3;
        private String location_full;
        private String last_login;
        private String aboutyou;
        private String phone;
        private String notification;
        private String occupation;
        private String langauge;
        private String follows;
        private String followers;
        private String lat;
        private String lon;

        public Data() {

        }


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_fb_id() {
            return user_fb_id;
        }

        public void setUser_fb_id(String user_fb_id) {
            this.user_fb_id = user_fb_id;
        }

        public String getLoginsource() {
            return loginsource;
        }

        public void setLoginsource(String loginsource) {
            this.loginsource = loginsource;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getServiceid() {
            return serviceid;
        }

        public void setServiceid(String serviceid) {
            this.serviceid = serviceid;
        }

        public String getUser_dob() {
            return user_dob;
        }

        public void setUser_dob(String user_dob) {
            this.user_dob = user_dob;
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

        public String getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(String user_sex) {
            this.user_sex = user_sex;
        }

        public String getUserregisterdate() {
            return userregisterdate;
        }

        public void setUserregisterdate(String userregisterdate) {
            this.userregisterdate = userregisterdate;
        }

        public String getUser_city() {
            return user_city;
        }

        public void setUser_city(String user_city) {
            this.user_city = user_city;
        }

        public String getLocation1() {
            return location1;
        }

        public void setLocation1(String location1) {
            this.location1 = location1;
        }

        public String getLocation2() {
            return location2;
        }

        public void setLocation2(String location2) {
            this.location2 = location2;
        }

        public String getLocation3() {
            return location3;
        }

        public void setLocation3(String location3) {
            this.location3 = location3;
        }

        public String getLocation_full() {
            return location_full;
        }

        public void setLocation_full(String location_full) {
            this.location_full = location_full;
        }

        public String getLast_login() {
            return last_login;
        }

        public void setLast_login(String last_login) {
            this.last_login = last_login;
        }

        public String getAboutyou() {
            return aboutyou;
        }

        public void setAboutyou(String aboutyou) {
            this.aboutyou = aboutyou;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNotification() {
            return notification;
        }

        public void setNotification(String notification) {
            this.notification = notification;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getLangauge() {
            return langauge;
        }

        public void setLangauge(String langauge) {
            this.langauge = langauge;
        }

        public String getFollows() {
            return follows;
        }

        public void setFollows(String follows) {
            this.follows = follows;
        }

        public String getFollowers() {
            return followers;
        }

        public void setFollowers(String followers) {
            this.followers = followers;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }
        public Data(Parcel in) {
            user_id = in.readString();
            user_email = in.readString();
            user_fb_id = in.readString();
            loginsource = in.readString();
            usertype = in.readString();
            serviceid = in.readString();
            user_dob = in.readString();
            user_fullname = in.readString();
            user_img = in.readString();
            user_sex = in.readString();
            userregisterdate = in.readString();
            user_city = in.readString();
            location1 = in.readString();
            location2 = in.readString();
            location3 = in.readString();
            location_full = in.readString();
            last_login = in.readString();
            aboutyou = in.readString();
            phone = in.readString();
            notification = in.readString();
            occupation = in.readString();
            langauge = in.readString();
            follows = in.readString();
            followers = in.readString();
            lat = in.readString();
            lon = in.readString();
        }


        @Override
        public int describeContents() {

            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(user_id);
            dest.writeString(user_email);
            dest.writeString(user_fb_id);
            dest.writeString(loginsource);
            dest.writeString(usertype);
            dest.writeString(serviceid);
            dest.writeString(user_dob);
            dest.writeString(user_fullname);
            dest.writeString(user_img);
            dest.writeString(user_sex);
            dest.writeString(userregisterdate);
            dest.writeString(user_city);
            dest.writeString(location1);
            dest.writeString(location2);
            dest.writeString(location3);
            dest.writeString(location_full);
            dest.writeString(last_login);
            dest.writeString(aboutyou);
            dest.writeString(phone);
            dest.writeString(notification);
            dest.writeString(occupation);
            dest.writeString(langauge);
            dest.writeString(follows);
            dest.writeString(followers);
            dest.writeString(lat);
            dest.writeString(lon);
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

        public static Data getInstance() {
            return new Data();
        }
    }

}
