package com.example.Terriffic.Incident.Model;

import com.example.Terriffic.SearchBot.Model.IncidentLink;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.sql.Timestamp;

public class IncidentDTO {
    private String incident_type;
    private String description;
    private Timestamp date_time;
    private double latitude;
    private double longitude;
    private Point location;
    private String reported_by;
    private final GeometryFactory geometryFactory;
    private IncidentLink incidentLink;

    public IncidentDTO() {
        this.geometryFactory = new GeometryFactory();
    }

    public IncidentDTO(
            String incident_type,
            String description,
            Timestamp date_time,
            double latitude,
            double longitude,
            String reported_by
    ) {
        this.incident_type = incident_type;
        this.description = description;
        this.date_time = date_time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reported_by = reported_by;
//        this.setLocation(longitude, latitude);

        this.geometryFactory = new GeometryFactory();
    }

    // Getters and Setters
    public String getIncident_type() {
        return incident_type;
    }

    public void setIncident_type(String incident_type) {
        this.incident_type = incident_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate_time() {
        return date_time;
    }

    public void setDate_time(Timestamp date_time) {
        this.date_time = date_time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getReported_by() {
        return reported_by;
    }

    public void setReported_by(String reported_by) {
        this.reported_by = reported_by;
    }

    public Point getLocation() {
        Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        location.setSRID(4326);
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setLocation(double longitude, double latitude) {
        this.location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        this.location.setSRID(4326);
    }

    public IncidentLink getIncidentLink() {
        return incidentLink;
    }

    public void setIncidentLink(IncidentLink incidentLink) {
        this.incidentLink = incidentLink;
    }
}
