package com.example.Terriffic.Incident.Model;

import com.example.Terriffic.SearchBot.Model.IncidentLink;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

import java.util.Date;

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

    private String location_name;

    private Date date_time;

    private String description;

    private String incident_type;

    @OneToOne
    @JoinColumn(name = "incident_link_id")
    private IncidentLink incident_link;

    public IncidentLink getIncident_link() {
        return incident_link;
    }

    public void setIncident_link(IncidentLink incidentLink) {
        this.incident_link = incidentLink;
    }

    public Incident() {

    }

    public Incident(String location_name, String incident_type, String description, Date date_time, Point location, String reported_by,
                    IncidentLink incidentLink
    ) {
        this.location_name = location_name;
        this.incident_type = incident_type;
        this.description = description;
        this.date_time = date_time;
        this.location = location;
        this.reported_by = reported_by;
        this.incident_link = incidentLink;
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

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
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

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
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
