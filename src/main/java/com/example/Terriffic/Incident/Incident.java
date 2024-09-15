package com.example.Terriffic.Incident;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.sql.Timestamp;

@Entity
@Table(name = "Incident")
public class Incident {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "reported_by")
    private String reported_by;
    @Column(name = "location", columnDefinition = "geometry(Point, 4326)")
    @Convert(converter = PointConverter.class)
    private Point location;
    @Column(name = "date_time")
    private Timestamp date_time;
    @Column(name = "descriptionn")
    private String description;
    @Column(name = "incident_type")
    private String incident_type;
    public Incident(){

    }
    public Incident(String incident_type, String description, Timestamp date_time, double latitude, double longitude, String reported_by) {
        this.incident_type = incident_type;
        this.description = description;
        this.date_time = date_time;
        /*this.location =*/
              Point temp =  (new GeometryFactory().createPoint(new Coordinate(longitude, latitude)));
              System.out.println(temp.getCoordinates());
        this.reported_by = reported_by;
    }

    public int getId() {
        return id;
    }


    public String getReported_by() {
        return reported_by;
    }

    public void setReported_by(String reported_by) {
        this.reported_by = reported_by;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(double longitude, double latitude) {
        this.location = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));
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
