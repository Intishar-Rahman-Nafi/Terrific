package com.example.Terriffic.SearchBot.Service;

import com.example.Terriffic.SearchBot.Model.IncidentLink;
import com.example.Terriffic.SearchBot.Repository.IncidentLinkRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class WebScrappingService {
    @Autowired
    private IncidentLinkRepository incidentLinkRepository;

    @Scheduled(fixedRate = 10000)
    public void fetchIncidentLinks() throws IOException {
        String url = "https://www.dhakatribune.com/topic/road-accident";
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");

        for (Element link : links) {
            String href = link.attr("href");

            String fullLink = "https://www.dhakatribune.com" + href;

            // Check if link already exists in the database
            Optional<IncidentLink> existingLink = incidentLinkRepository.findByLink(fullLink);
            if (existingLink.isEmpty()) {
                saveIncidentLink(fullLink);  // Save only if it doesn't exist
            }
        }
    }
    private void saveIncidentLink(String link) {
        IncidentLink incidentLink = new IncidentLink(link);
        incidentLinkRepository.save(incidentLink);
    }
}
