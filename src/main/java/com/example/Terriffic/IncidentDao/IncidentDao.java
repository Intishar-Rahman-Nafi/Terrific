package com.example.Terriffic.IncidentDao;

import com.example.Terriffic.Incident.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncidentDao extends JpaRepository<Incident, Integer> {
    @Query(value = "SELECT * FROM Incident i WHERE ST_DWithin(i.location, ST_MakePoint(:lonMin, :latMin), ST_Distance(ST_MakePoint(:lonMin, :latMin), ST_MakePoint(:lonMax, :latMax)))", nativeQuery = true)
    List<Incident> findWithinBoundingBox(
            @Param("latMin") double latMin,
            @Param("lonMin") double lonMin,
            @Param("latMax") double latMax,
            @Param("lonMax") double lonMax
    );
}
