package com.regmoraes.closer.data.entity;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class Reminder {

    public Integer id;
    public String title;
    public String description;
    public String locationName;
    public Double lat;
    public Double lng;

    public Reminder(Integer id, String title, String description, String locationName, Double lat, Double lng) {
        this.id = id;
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
}
