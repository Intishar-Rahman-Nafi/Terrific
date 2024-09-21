package com.example.Terriffic.SearchBot.Service.NewsAgency;

import com.example.Terriffic.Incident.Model.Incident;
import com.example.Terriffic.SearchBot.Model.IncidentLink;
import com.example.Terriffic.SearchBot.Service.AiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.example.Terriffic.SearchBot.Model.NewsAgency;


public class DhakaTribune implements NewsAgencyInterface {


    @Override
    public Optional<List<IncidentLink>> getNewIncidentLinks() {
        List<IncidentLink> incidentLinks = new ArrayList<>(List.of());
        try {
            String NEWS_URI = "https://www.dhakatribune.com/api/theme_engine/get_ajax_contents?widget=612&start=0&count=22&tags=976";
            // Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(NEWS_URI))
                    .GET()
                    .build();

            // Send request and get response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Process JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());

            // Example: Print a specific field from the JSON response
            System.out.println(jsonNode.get("html").asText());

            // Parse and get Document using jsoup
            Document doc = Jsoup.parse(jsonNode.get("html").asText());
            Elements links = doc.select(".title > a");
            System.out.println("Found " + links.size() + " links");
            int count = 1;
            for (Element link : links) {
                String href = link.attr("href");
                String fullLink = "https:" + href.strip();
                System.out.println("("+ count++ +"/"+ links.size() +")" + " Processing link: " + fullLink);
                incidentLinks.add(new IncidentLink(NewsAgency.DHAKA_TRIBUNE, fullLink));
            }

            return Optional.of(incidentLinks);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Incident> parseIncidentNewsPage(IncidentLink link) {
        try {
            Document doc = Jsoup.connect(link.getLink()).get();
            Element articleBody = doc.select("div[itemprop=articleBody]").first();

            if (articleBody != null) {
                String content = articleBody.text();
                String headline = doc.select("h1").text();
                String dateString = doc.select("span[itemprop=datePublished]").attr("content");
                Date date = this.parseDate(dateString);

                System.out.println("Headline: " + headline);
                System.out.println("Date: " + date);
                System.out.println("Content: " + content);


//                AiService aiService = new AiService();
//                String LocationAndIncident = aiService.determineLocationAndIncidentType(headline, date, content);
//                //Incident type and Location  seperate kor NAIMMMMMMMMMMMMMMMMMMMM
//                String location = "";
//                String Incident_type = "";
//                //Incident type and Location  seperate kor NAIMMMMMMMMMMMMMMMMMMMM

                //                incidentService.saveParsedIncident(location, Incident_type);
                //Naimmmmmmm
            } else {
                System.out.println("Article body not found for link: " + link.getLink());
            }
            return Optional.empty();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Date parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, formatter);
        return Date.from(zonedDateTime.toInstant());
    }
}
