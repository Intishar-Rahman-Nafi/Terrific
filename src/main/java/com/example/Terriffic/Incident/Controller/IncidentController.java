package com.example.Terriffic.Incident.Controller;

import com.example.Terriffic.Incident.Model.Incident;
import com.example.Terriffic.Incident.Service.IncidentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class IncidentController {
    public IncidentServiceImp inciServ;
    @Autowired
    public IncidentController(IncidentServiceImp inciserv){
        inciServ = inciserv;
    }

    @GetMapping("/incident")
    public List<Incident> findAll(){
        return inciServ.findAll();
    }

    @GetMapping("/incident/range")
    public List<Incident> findIncidentsWithinBoundingBox(
            @RequestParam("lat_min") double latMin,
            @RequestParam("lon_min") double lonMin,
            @RequestParam("lat_max") double latMax,
            @RequestParam("lon_max") double lonMax
    ) {
        return inciServ.findWithinBoundingBox(latMin, lonMin, latMax, lonMax);
    }
    @GetMapping("/incident/{Id}")
    public Incident findById(@PathVariable int Id){
        return inciServ.findById(Id);
    }

    @PostMapping("/incident")
    public Incident insert( @RequestBody Incident incident){
        if (incident.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location cannot be null");
        }
        return inciServ.save(incident);
    }

    @PutMapping("/incident")
    public Incident update( @RequestBody Incident incident){
        return inciServ.save(incident);
    }

    @DeleteMapping("/incident/{Id}")
    public void deleteById(@PathVariable int Id){
        inciServ.deleteById(Id);
        return;
    }
}
