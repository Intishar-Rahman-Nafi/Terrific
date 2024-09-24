package com.example.Terriffic.SearchBot.Service;

import com.example.Terriffic.Incident.Model.Incident;
import com.example.Terriffic.Incident.Service.IncidentService;
import com.example.Terriffic.SearchBot.Model.IncidentLink;
import com.example.Terriffic.SearchBot.Model.IncidentLinkStatus;
import com.example.Terriffic.SearchBot.Repository.IncidentLinkRepository;
import com.example.Terriffic.SearchBot.Service.NewsAgency.DhakaTribune;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class IncidentParserService {
    private final IncidentLinkRepository incidentLinkRepository;
    private final IncidentService incidentService;
    private final DhakaTribune dhakaTribune;

    public IncidentParserService(IncidentLinkRepository incidentLinkRepository, IncidentService incidentService, DhakaTribune dhakaTribune) {
        this.incidentLinkRepository = incidentLinkRepository;
        this.incidentService = incidentService;
        this.dhakaTribune = dhakaTribune;
    }

    @Scheduled(fixedRate = 1000 * 60 * 70)
    public void parseUnfetchedLinks() {
        List<IncidentLink> processedLinks = new java.util.ArrayList<>(List.of());
        try {
            List<IncidentLink> pendingLinks = incidentLinkRepository.findByStatus(IncidentLinkStatus.PENDING);
            System.out.println("Found " + pendingLinks.size() + " pending links");
            int count = 0;
            for (IncidentLink link : pendingLinks) {
                System.out.println("(" + ++count + "/" + pendingLinks.size() + ")" + "Processing link: " + link.getLink());
                link.setLink(cleanURL(link.getLink()));

                switch (link.getNewsAgency()) {
                    case DHAKA_TRIBUNE:
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

                // wait for 5 seconds before processing the next link
                Thread.sleep(5000);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            incidentLinkRepository.saveAll(processedLinks);
            System.out.println(processedLinks.size() + " links processed successfully");
        }
    }

    private static String cleanURL(String url) {
        // Decode the URL
        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }
}
