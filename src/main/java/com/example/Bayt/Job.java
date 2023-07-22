package com.example.Bayt;

public class Job {
    private String title;
    private String location;
    private double latitude;
    private double longitude;

    public Job(String title, String location, double latitude, double longitude) {
        this.title = title;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

