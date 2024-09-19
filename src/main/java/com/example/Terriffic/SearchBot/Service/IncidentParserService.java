package com.example.Terriffic.SearchBot.Service;

import com.example.Terriffic.Incident.Service.IncidentService;
import com.example.Terriffic.SearchBot.Model.IncidentLink;
import com.example.Terriffic.SearchBot.Repository.IncidentLinkRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class IncidentParserService {
    @Autowired
    private IncidentLinkRepository incidentLinkRepository;
    @Autowired
    IncidentService incidentService;

    public void parseUnfetchedLinks() throws IOException {
        List<IncidentLink> unfetchedLinks = incidentLinkRepository.findByTemp(0);

        for (IncidentLink link : unfetchedLinks) {
            Document doc = Jsoup.connect(link.getLink()).get();
            Element articleBody = doc.select("div[itemprop=articleBody]").first();

            if (articleBody != null) {
                String content = articleBody.text();  // Get all text inside the article body
                String headline = doc.select("h1").text();  // Assuming h1 is the headline
                String date = doc.select(".date").text();  // Assuming the date has a specific class
                //NAIMMMMMMM
                AiService aiService = new AiService();
                String LocationAndIncident = aiService.determineLocationAndIncidentType(headline, date, content);
                //Incident type and Location  seperate kor NAIMMMMMMMMMMMMMMMMMMMM
                String location = "";
                String Incident_type = "";
                //Incident type and Location  seperate kor NAIMMMMMMMMMMMMMMMMMMMM

                incidentService.saveParsedIncident(location, Incident_type);
                //Naimmmmmmm
            } else {
                System.out.println("Article body not found for link: " + link.getLink());
            }
        }
    }

}
