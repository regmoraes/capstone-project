package com.regmoraes.closer.presentation.addreminder;

import com.regmoraes.closer.data.Reminder;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class ReminderData {

    private String title;
    private String description;
    private String locationName;
    private Double latitude;
    private Double longitude;

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

    public static Reminder createReminder(ReminderData reminderData) {

        return new Reminder(null,
                reminderData.title,
                reminderData.description,
                reminderData.locationName,
                reminderData.latitude,
                reminderData.longitude);
    }
}
