package com.regmoraes.closer.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class Reminder implements Parcelable {

    public Integer id;
    public String title;
    public String description;
    public String locationName;
    public Double lat;
    public Double lng;

    public Reminder() { }

    public Reminder(Integer id, String title, String description, String locationName, Double lat, Double lng) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.lat = lat;
        this.lng = lng;
    }

    public Reminder(String title, String description, String locationName, Double lat, Double lng) {
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.lat = lat;
        this.lng = lng;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocationName() {
        return locationName;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.locationName);
        dest.writeValue(this.lat);
        dest.writeValue(this.lng);
    }

    protected Reminder(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.description = in.readString();
        this.locationName = in.readString();
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.lng = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Reminder> CREATOR = new Parcelable.Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel source) {
            return new Reminder(source);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };
}
