package com.appzollo.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yogasaikrishna on 09-12-2014.
 */
public class EventCostPlan implements Parcelable {
    private String id;
    private String eventid;
    private String cost;
    private String details;
    private String currency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getEventid() {
        return eventid;
    }

    public void seteventid(String eventid) {
        this.eventid =eventid;
    }

    public String getcost() {
        return cost;
    }
    public void setcost(String cost) {
        this.cost =cost;
    }


    public String getdetails() {
       return details;
    }


    public void setdetails(String details) {
        this.details = details;
    }

    public String getcurrency() {
        return currency;
    }


    public void setcurrency(String currency) {
        this.currency = currency;
    }


    public EventCostPlan() {

    }

    public EventCostPlan(Parcel in) {
        id = in.readString();
        eventid = in.readString();
        cost = in.readString();
        details = in.readString();
        currency = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(id);
        dest.writeString(eventid);
        dest.writeString(cost);
        dest.writeString(details);
        dest.writeString(currency);

    }

    public static final Parcelable.Creator<EventCostPlan> CREATOR = new Parcelable.Creator<EventCostPlan>() {

        @Override
        public EventCostPlan createFromParcel(Parcel source) {
            return new EventCostPlan(source);
        }

        @Override
        public EventCostPlan[] newArray(int size) {
            return new EventCostPlan[size];
        }
    };

}