package com.regmoraes.closer.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Entity(tableName = "reminders")
public class Reminder {

    public static final String REMINDER_ID = "reminder-id";

    @PrimaryKey(autoGenerate = true)
    private Integer uid;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String description;

    @ColumnInfo
    private String locationName;

    @ColumnInfo
    private Double latitude;

    @ColumnInfo
    private Double longitude;

    @Ignore
    public Reminder(){}

    public Reminder(Integer uid, String title, String description, String locationName, Double latitude, Double longitude) {
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
