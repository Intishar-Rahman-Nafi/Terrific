package com.example.Terriffic.SearchBot.Repository;

import com.example.Terriffic.SearchBot.Model.IncidentLink;
import com.example.Terriffic.SearchBot.Model.IncidentLinkStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncidentLinkRepository extends JpaRepository<IncidentLink, Long> {
    List<IncidentLink> findByStatus(IncidentLinkStatus status);
    Optional<IncidentLink> findByLink(String link);
}
