package com.example.Terriffic.SearchBot.Model;

import jakarta.persistence.*;


@Entity
@Table
public class IncidentLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NewsAgency newsAgency;

    private String link;

    @Enumerated(EnumType.STRING)
    private IncidentLinkStatus status = IncidentLinkStatus.PENDING;

    public IncidentLink(){}
    public IncidentLink(NewsAgency newsAgency, String link) {
        this.newsAgency = newsAgency;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public IncidentLinkStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentLinkStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NewsAgency getNewsAgency() {
        return newsAgency;
    }

    public void setNewsAgency(NewsAgency newsAgency) {
        this.newsAgency = newsAgency;
    }
}
