package com.example.Terriffic.SearchBot.Service.NewsAgency;

import com.example.Terriffic.SearchBot.Model.IncidentLink;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
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
}
