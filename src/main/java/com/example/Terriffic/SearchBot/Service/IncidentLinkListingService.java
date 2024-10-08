package com.example.Terriffic.SearchBot.Service;

import com.example.Terriffic.SearchBot.Model.IncidentLink;
import com.example.Terriffic.SearchBot.Model.NewsAgency;
import com.example.Terriffic.SearchBot.Repository.IncidentLinkRepository;
import com.example.Terriffic.SearchBot.Service.NewsAgency.DhakaTribune;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class IncidentLinkListingService {
    @Autowired
    private IncidentLinkRepository incidentLinkRepository;

    private final DhakaTribune dhakaTribune;

    public IncidentLinkListingService(IncidentLinkRepository incidentLinkRepository, DhakaTribune dhakaTribune) {
        this.incidentLinkRepository = incidentLinkRepository;
        this.dhakaTribune = dhakaTribune;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void fetchIncidentLinks() throws IOException {
        Optional<List<IncidentLink>> dhakaTribuneLinks = this.dhakaTribune.getNewIncidentLinks();

        dhakaTribuneLinks.ifPresent(incidentLinks -> {
            // save all links that are not already in the database
            System.out.println(incidentLinks.size() + " Incident links fetched successfully");
            AtomicInteger newCount = new AtomicInteger();
            incidentLinks.forEach(incLink -> {
                try {
                    if (incidentLinkRepository.findByLink(incLink.getLink()).isEmpty()) {
                        newCount.getAndIncrement();
                        saveIncidentLink(incLink.getLink());
                    }
                } catch (Exception e) {
                    System.out.println("Error while saving link: " + incLink.getLink());
                    e.printStackTrace();
                }
            });
            System.out.println(newCount.get() + " new links saved");
        });
    }

    private void saveIncidentLink(String link) {
        IncidentLink incidentLink = new IncidentLink(NewsAgency.DHAKA_TRIBUNE, link);
        incidentLinkRepository.save(incidentLink);
    }
}
