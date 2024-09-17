package com.example.Terriffic.Incident.Controller;

import com.example.Terriffic.Incident.Model.Incident;
import com.example.Terriffic.Incident.Model.IncidentDTO;
import com.example.Terriffic.Incident.Service.IncidentServiceImp;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidents")
public class IncidentController {
    public IncidentServiceImp incidentService;

    @Autowired
    public IncidentController(IncidentServiceImp incidentService){
        this.incidentService = incidentService;

    }

    @GetMapping()
    public List<Incident> findAll(){
        return incidentService.findAll();
    }

    @GetMapping("/range")
    public List<Incident> findIncidentsWithinBoundingBox(
            @RequestParam("lat_min") double latMin,
            @RequestParam("lon_min") double lonMin,
            @RequestParam("lat_max") double latMax,
            @RequestParam("lon_max") double lonMax
    ) {
        return incidentService.findWithinBoundingBox(latMin, lonMin, latMax, lonMax);
    }
    @GetMapping("/{Id}")
    public Incident findById(@PathVariable Long Id){
        return incidentService.findById(Id);
    }

    @PostMapping()
    public ResponseEntity<Incident> insert( @RequestBody IncidentDTO request){
        Incident incident = new Incident();
        incident.setIncident_type(request.getIncident_type());
        incident.setDescription(request.getDescription());
        incident.setDate_time(request.getDate_time());
        incident.setReported_by(request.getReported_by());
        incident.setLocation(request.getLocation());

        try {
            Incident createdIncident = incidentService.save(incident);
            if (createdIncident == null) {
                throw new Exception("Incident not created");
            }
            return new ResponseEntity<Incident>(createdIncident, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{Id}")
    public Incident update( @RequestBody Incident incident, @PathVariable Long Id){
        return incidentService.save(incident);
    }

    @DeleteMapping("/{Id}")
    public void deleteById(@PathVariable Long Id){
        incidentService.deleteById(Id);
        return;
    }
}
