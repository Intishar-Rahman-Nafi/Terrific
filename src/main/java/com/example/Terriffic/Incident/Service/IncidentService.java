package com.example.Terriffic.Incident.Service;

import com.example.Terriffic.Incident.Model.Incident;

import java.util.List;

public interface IncidentService {
    public Incident save(Incident incident);
    public void deleteById(Long id);
    public List<Incident> findAll();
    public Incident findById(Long id);
    public List<Incident> findWithinBoundingBox(double latMin, double lonMin, double latMax, double lonMax);
    public void saveParsedIncident(String location, String incidentType);
}
