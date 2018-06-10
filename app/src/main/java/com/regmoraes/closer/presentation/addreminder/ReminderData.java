package com.regmoraes.closer.presentation.addreminder;

import android.os.Parcel;
import android.os.Parcelable;

import com.regmoraes.closer.data.Reminder;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class ReminderData implements Parcelable {

    private Integer uid;
    private String title;
    private String description;
    private String locationName;
    private Double latitude;
    private Double longitude;

    public ReminderData() {
    }

    public ReminderData(Integer uid, String title, String description, String locationName,
                        Double latitude, Double longitude) {
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Reminder createReminder(ReminderData reminderData) {

        return new Reminder(reminderData.uid, reminderData.title, reminderData.description,
                reminderData.locationName, reminderData.latitude, reminderData.longitude);
    }

    public static ReminderData fromReminder(Reminder reminder) {

        return new ReminderData(reminder.getUid(), reminder.getTitle(), reminder.getDescription(),
                reminder.getLocationName(), reminder.getLatitude(), reminder.getLongitude());
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

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getUid() {
        return uid;
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

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.uid);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.locationName);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
    }

    protected ReminderData(Parcel in) {
        this.uid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.description = in.readString();
        this.locationName = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<ReminderData> CREATOR = new Parcelable.Creator<ReminderData>() {
        @Override
        public ReminderData createFromParcel(Parcel source) {
            return new ReminderData(source);
        }

        @Override
        public ReminderData[] newArray(int size) {
            return new ReminderData[size];
        }
    };
}
