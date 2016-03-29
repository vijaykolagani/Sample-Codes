package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yogasaikrishna on 09-12-2014.
 */
public class EventDetail implements Parcelable {
    private String id;
    private String title;
    private String where_area;
    private List<EventData> segment;
    private String date_from;
    private String date_to;
    private String custome_date;
    private String date;
    private String cost;
    private String travel;
    private String accommodation;
    private String food;
    private String other;
    private String latitude_creat;
    private String longitude_creat;
    private String city_creat;
    private String user_id;
    private String user_fullname;
    private String user_img;
    private String description;
    private String departure_date;
    private String timings;
    private String cost_description;
    private String fixed_charge;
    private String capacity;
    private String seats;
    private String duration;
    private String steps;
    private String visitors;
    private String booking_facility;
    private String difficulty_level;
    private String istravel;
    private String isfood;
    private String isaccommodation;
    private String live;
    private String full_creat;
    private ArrayList<String> image;
    private String locationtag_full;
    private String locationtag_lat;
    private String locationtag_lon;
    private String cost_itinerary;
    private List<EventCostPlan> costplans;
    private String comments;
    private String comments_status;
    private String intrests;
    private String intrest_status;
    private String timeago;

    public EventDetail() {
        segment = new ArrayList<EventData>();
        image = new ArrayList<String>();
        costplans = new ArrayList<EventCostPlan>();
    }

    public EventDetail(Parcel in) {
        this();
        id = in.readString();
        title = in.readString();
        where_area = in.readString();
        in.readTypedList(segment, EventData.CREATOR);
        date_from = in.readString();
        date_to = in.readString();
        custome_date = in.readString();
        date = in.readString();
        cost = in.readString();
        travel = in.readString();
        accommodation = in.readString();
        food = in.readString();
        other = in.readString();
        latitude_creat = in.readString();
        longitude_creat = in.readString();
        city_creat = in.readString();
        user_id = in.readString();
        user_fullname = in.readString();
        user_img = in.readString();
        description = in.readString();
        departure_date = in.readString();
        timings = in.readString();
        cost_description = in.readString();
        fixed_charge = in.readString();
        capacity = in.readString();
        seats = in.readString();
        duration = in.readString();
        steps = in.readString();
        visitors = in.readString();
        booking_facility = in.readString();
        difficulty_level = in.readString();
        istravel = in.readString();
        isfood = in.readString();
        isaccommodation = in.readString();
        live = in.readString();
        full_creat = in.readString();
        image = (ArrayList<String>) in.readSerializable();
        locationtag_full = in.readString();
        locationtag_lat = in.readString();
        locationtag_lon = in.readString();
        cost_itinerary = in.readString();
        in.readTypedList(costplans, EventCostPlan.CREATOR);
        comments = in.readString();
        comments_status = in.readString();
        intrests = in.readString();
        intrest_status = in.readString();
        timeago = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWhere_area() {
        return where_area;
    }

    public void setWhere_area(String where_area) {
        this.where_area = where_area;
    }

    public List<EventData> getSegment() {
        return segment;
    }

    public void setSegment(List<EventData> segment) {
        this.segment = segment;
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

    public String getCustome_date() {
        return custome_date;
    }

    public void setCustome_date(String custome_date) {
        this.custome_date = custome_date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getCost_description() {
        return cost_description;
    }

    public void setCost_description(String cost_description) {
        this.cost_description = cost_description;
    }

    public String getFixed_charge() {
        return fixed_charge;
    }

    public void setFixed_charge(String fixed_charge) {
        this.fixed_charge = fixed_charge;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getVisitors() {
        return visitors;
    }

    public void setVisitors(String visitors) {
        this.visitors = visitors;
    }

    public String getBooking_facility() {
        return booking_facility;
    }

    public void setBooking_facility(String booking_facility) {
        this.booking_facility = booking_facility;
    }

    public String getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(String difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public String getIstravel() {
        return istravel;
    }

    public void setIstravel(String istravel) {
        this.istravel = istravel;
    }

    public String getIsfood() {
        return isfood;
    }

    public void setIsfood(String isfood) {
        this.isfood = isfood;
    }

    public String getIsaccommodation() {
        return isaccommodation;
    }

    public void setIsaccommodation(String isaccommodation) {
        this.isaccommodation = isaccommodation;
    }
    public String getLive(){
        return live;
    }
    public void setLive(String live){this.live = live;}
    public String getFull_creat() {
        return full_creat;
    }

    public void setFull_creat(String full_creat) {
        this.full_creat = full_creat;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }
    public String getLocatio_full() {
        return locationtag_full;
    }

    public void setLocatio_full(String locationtag_full) {
        this.locationtag_full = locationtag_full;
    }
    public String getLocationtag_lat() {
        return locationtag_lat;
    }

    public void setLocationtag_lat(String locationtag_lat) {
        this.locationtag_lat = locationtag_lat;
    }
    public String getLocationtag_lon() {
        return locationtag_lon;
    }

    public void setLocationtag_lon(String locationtag_lon) {
        this.locationtag_lon = locationtag_lon;
    }

    public String getCost_itinerary() {
        return cost_itinerary;
    }

    public void setCost_itinerary(String cost_itinerary) {
        this.cost_itinerary = cost_itinerary;
    }

    public List<EventCostPlan> getCostplans() {
        return costplans;
    }

    public void setCostplans(List<EventCostPlan> costplans) {
        this.costplans = costplans;
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

    public String getIntrests() {
        return intrests;
    }

    public void setIntrests(String intrests) {
        this.intrests = intrests;
    }

    public String getIntrest_status() {
        return intrest_status;
    }

    public void setIntrest_status(String intrest_status) {
        this.intrest_status = intrest_status;
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
        dest.writeString(title);
        dest.writeString(where_area);
        dest.writeTypedList(segment);
        dest.writeString(date_from);
        dest.writeString(date_to);
        dest.writeString(custome_date);
        dest.writeString(date);
        dest.writeString(cost);
        dest.writeString(travel);
        dest.writeString(accommodation);
        dest.writeString(food);
        dest.writeString(other);
        dest.writeString(latitude_creat);
        dest.writeString(longitude_creat);
        dest.writeString(city_creat);
        dest.writeString(user_id);
        dest.writeString(user_fullname);
        dest.writeString(user_img);
        dest.writeString(description);
        dest.writeString(departure_date);
        dest.writeString(timings);
        dest.writeString(cost_description);
        dest.writeString(fixed_charge);
        dest.writeString(capacity);
        dest.writeString(seats);
        dest.writeString(duration);
        dest.writeString(steps);
        dest.writeString(visitors);
        dest.writeString(booking_facility);
        dest.writeString(difficulty_level);
        dest.writeString(istravel);
        dest.writeString(isfood);
        dest.writeString(isaccommodation);
        dest.writeString(live);
        dest.writeString(full_creat);
        dest.writeSerializable(image);
        dest.writeString(locationtag_full);
        dest.writeString(locationtag_lat);
        dest.writeString(locationtag_lon);
        dest.writeString(cost_itinerary);
        dest.writeTypedList(costplans);
        dest.writeString(comments);
        dest.writeString(comments_status);
        dest.writeString(intrests);
        dest.writeString(intrest_status);
        dest.writeString(timeago);
    }

    public static final Parcelable.Creator<EventDetail> CREATOR = new Parcelable.Creator<EventDetail>() {

        @Override
        public EventDetail createFromParcel(Parcel source) {
            return new EventDetail(source);
        }

        @Override
        public EventDetail[] newArray(int size) {
            return new EventDetail[size];
        }
    };
}