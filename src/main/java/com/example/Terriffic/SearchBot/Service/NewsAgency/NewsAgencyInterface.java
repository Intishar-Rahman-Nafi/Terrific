package com.example.Terriffic.SearchBot.Service.NewsAgency;

import com.example.Terriffic.Incident.Model.Incident;
import com.example.Terriffic.SearchBot.Model.IncidentLink;

import java.util.List;
import java.util.Optional;

public interface NewsAgencyInterface {
    public Optional<List<IncidentLink>> getNewIncidentLinks();
    public Optional<Incident> parseIncidentNewsPage(IncidentLink link);
}
