package com.example.Terriffic.SearchBot.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Incident_link")
public class IncidentLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "link")
    private String link;
    @Column(name = "temp")
    private int temp = 0;
    public IncidentLink(){

    }
    public IncidentLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
