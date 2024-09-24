package com.example.Terriffic.SearchBot.Service;

public class AnalyzeNewsResponse {
    private String location_name;
    private String type;

    public AnalyzeNewsResponse(String location_name, String type) {
        this.location_name = location_name;
        this.type = type;
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
}
