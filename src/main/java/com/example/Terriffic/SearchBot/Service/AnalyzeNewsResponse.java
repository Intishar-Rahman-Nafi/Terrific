package com.example.Terriffic.SearchBot.Service;

public class AnalyzeNewsResponse {
    private String location_name;
    private String type;
    private String location_longitude;
    private String location_latitude;

    public AnalyzeNewsResponse(String location_name, String type, String location_longitude, String location_latitude) {
        this.location_name = location_name;
        this.type = type;
        this.location_longitude = location_longitude;
        this.location_latitude = location_latitude;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation_longitude() {
        return location_longitude;
    }

    public void setLocation_longitude(String location_longitude) {
        this.location_longitude = location_longitude;
    }

    public String getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(String location_latitude) {
        this.location_latitude = location_latitude;
    }
}
