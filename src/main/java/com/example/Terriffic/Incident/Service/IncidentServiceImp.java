package com.example.Terriffic.Incident.Service;

import com.example.Terriffic.Incident.Model.Incident;
import com.example.Terriffic.Incident.Repository.IncidentDao;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class IncidentServiceImp implements IncidentService {
    public IncidentDao incidentdao;

    @Autowired
    public IncidentServiceImp(IncidentDao incidentDao) {
        incidentdao = incidentDao;
    }

    @Override
    @Transactional
    public Incident save(Incident incident) {
        return incidentdao.save(incident);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        incidentdao.deleteById(id);
    }

    @Override
    public List<Incident> findAll() {
        return incidentdao.findAll();
    }

    @Override
    @Transactional
    public List<Incident> findWithinBoundingBox(double latMin, double lonMin, double latMax, double lonMax) {
        System.out.println("latMin: " + latMin + " lonMin: " + lonMin + " latMax: " + latMax + " lonMax: " + lonMax);
        return incidentdao.findWithinBoundingBox(latMin, lonMin, latMax, lonMax);
    }

    @Override
    public Incident findById(Long id) {
        Optional<Incident> option = incidentdao.findById(id);
        return option.orElse(null);
    }

    @Override
    public void saveAll(List<Incident> newIncidents) {
        incidentdao.saveAll(newIncidents);
    }
}
