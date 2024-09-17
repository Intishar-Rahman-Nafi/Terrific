package com.example.Terriffic.Incident.Model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

import java.sql.Timestamp;

@Entity
@Table(name = "Incident")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reported_by;

    @Column(columnDefinition = "BLOB")
    @Convert(converter = PointConverter.class)
    private Point location;

    private Timestamp date_time;

    private String description;

    private String incident_type;

    public Incident(){

    }
    public Incident(String incident_type, String description, Timestamp date_time, Point location, String reported_by) {
        this.incident_type = incident_type;
        this.description = description;
        this.date_time = date_time;
        this.location = location;
        this.reported_by = reported_by;
    }

    public Long getId() {
        return id;
    }


    public String getReported_by() {
        return reported_by;
    }

    public void setReported_by(String reported_by) {
        this.reported_by = reported_by;
    }

    public Point getLocation() {
        return this.location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Timestamp getDate_time() {
        return date_time;
    }

    public void setDate_time(Timestamp date_time) {
        this.date_time = date_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIncident_type() {
        return incident_type;
    }

    public void setIncident_type(String incident_type) {
        this.incident_type = incident_type;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", reported_by='" + reported_by + '\'' +
                ", location='" + location + '\'' +
                ", date_time=" + date_time +
                ", description='" + description + '\'' +
                ", incident_type='" + incident_type + '\'' +
                '}';
    }
}
