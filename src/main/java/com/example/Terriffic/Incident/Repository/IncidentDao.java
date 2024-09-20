package com.example.Terriffic.Incident.Repository;

import com.example.Terriffic.Incident.Model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncidentDao extends JpaRepository<Incident, Long> {
    @Query(value = "SELECT * FROM incident i WHERE ST_Contains(ST_Envelope(ST_GeomFromText(CONCAT('LineString(', :lonMin, ' ', :latMin, ',', :lonMax, ' ', :latMax, ')'))), ST_GeomFromWKB(i.location));", nativeQuery = true)
    List<Incident> findWithinBoundingBox(
            @Param("latMin") double latMin,
            @Param("lonMin") double lonMin,
            @Param("latMax") double latMax,
            @Param("lonMax") double lonMax
    );
}
