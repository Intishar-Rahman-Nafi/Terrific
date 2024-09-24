package com.example.Terriffic.SearchBot.Service.NewsAgency;

import com.example.Terriffic.Incident.Model.Incident;
import com.example.Terriffic.SearchBot.Model.IncidentLink;
import com.example.Terriffic.SearchBot.Service.AiService;
import com.example.Terriffic.SearchBot.Service.AnalyzeNewsResponse;
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
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

@Service
public class DhakaTribune implements NewsAgencyInterface {

    private final AiService aiService;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public DhakaTribune(AiService aiService) {
        this.aiService = aiService;
    }

    @Override
    public Optional<List<IncidentLink>> getNewIncidentLinks() {
        List<IncidentLink> incidentLinks = new ArrayList<>(List.of());
        try {
            String NEWS_URI = "https://www.dhakatribune.com/api/theme_engine/get_ajax_contents?widget=612&start=0&count=250&tags=976";
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


                Optional<AnalyzeNewsResponse> news = aiService.analyzeNews(headline, content);
                if (news.isPresent()) {
                    AnalyzeNewsResponse analyzeNewsResponse = news.get();
                    System.out.println("location_name: " + analyzeNewsResponse.getLocation_name());
                    System.out.println("type: " + analyzeNewsResponse.getType());
                    System.out.println("location_longitude: " + analyzeNewsResponse.getLocation_longitude());
                    System.out.println("location_latitude: " + analyzeNewsResponse.getLocation_latitude());
                    double longitude = Double.parseDouble(analyzeNewsResponse.getLocation_longitude());
                    double latitude = Double.parseDouble(analyzeNewsResponse.getLocation_latitude());
                    Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
                    location.setSRID(4326);

                    Incident incident = new Incident(
                            analyzeNewsResponse.getLocation_name(),
                            analyzeNewsResponse.getType(),
                            headline,
                            date,
                            location,
                            NewsAgency.DHAKA_TRIBUNE.toString(),
                            link
                    );
                    return Optional.of(incident);
                }

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
