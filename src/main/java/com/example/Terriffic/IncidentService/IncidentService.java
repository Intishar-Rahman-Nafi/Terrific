package com.example.Terriffic.IncidentService;

import com.example.Terriffic.Incident.Incident;
import com.example.Terriffic.IncidentDao.IncidentDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IncidentService {
    public Incident save(Incident incident);
    public void deleteById(int id);
    public List<Incident> findAll();
    public Incident findById(int id);
    public List<Incident> findWithinBoundingBox(double latMin, double lonMin, double latMax, double lonMax);
}
