package com.example.Terriffic.SearchBot.Service;

import com.example.Terriffic.Incident.Model.Incident;
import com.example.Terriffic.Incident.Service.IncidentService;
import com.example.Terriffic.SearchBot.Model.IncidentLink;
import com.example.Terriffic.SearchBot.Model.IncidentLinkStatus;
import com.example.Terriffic.SearchBot.Repository.IncidentLinkRepository;
import com.example.Terriffic.SearchBot.Service.NewsAgency.DhakaTribune;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class IncidentParserService {
    @Autowired
    private IncidentLinkRepository incidentLinkRepository;
    @Autowired
    IncidentService incidentService;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void parseUnfetchedLinks() {
        List<IncidentLink> processedLinks = new java.util.ArrayList<>(List.of());
        try {
            List<IncidentLink> pendingLinks = incidentLinkRepository.findByStatus(IncidentLinkStatus.PENDING);
            System.out.println("Found " + pendingLinks.size() + " pending links");
            int count = 0;
            for (IncidentLink link : pendingLinks) {
                System.out.println("(" + ++count + "/" + pendingLinks.size() + ")" + "Processing link: " + link.getLink());

                switch (link.getNewsAgency()) {
                    case DHAKA_TRIBUNE:
                        DhakaTribune dhakaTribune = new DhakaTribune();
                        Optional<Incident> incident = dhakaTribune.parseIncidentNewsPage(link);
                        incident.ifPresent(inc -> {
                            incidentService.save(inc);
                            link.setStatus(IncidentLinkStatus.PROCESSED);
                            processedLinks.add(link);
                        });
                        break;
                    default:
                        System.out.println("News agency not supported: " + link.getNewsAgency());
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            incidentLinkRepository.saveAll(processedLinks);
            System.out.println(processedLinks.size() + " links processed successfully");
        }
    }

}
