package com.example.Terriffic.SearchBot.Service;

import com.example.Terriffic.Incident.Model.Incident;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

@Service
public class AiService {

    private final Gson gson;
    @Value("${ai.api_url}")
    private String AI_API_URL;

    public AiService(Gson gson) {
        this.gson = gson;
    }

    public Optional<AnalyzeNewsResponse> analyzeNews(String headline, String content) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(30))
                    .build();

            URI uri = new URI(AI_API_URL + "/analyze-news");

            AnalyzeNewsRequest analyzeNewsRequest = new AnalyzeNewsRequest(headline, content);
            String json = gson.toJson(analyzeNewsRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() != 200) {
                System.out.println("Error: Received non-OK response code " + response.statusCode());
                return Optional.empty();
            }

            if (response.body() == null || response.body().isEmpty()) {
                System.out.println("Error: Received empty response");
                return Optional.empty();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            System.out.println("location_name: " + jsonNode.get("location_name").asText());
            System.out.println("type: " + jsonNode.get("type").asText());

            AnalyzeNewsResponse analyzeNewsResponse = new AnalyzeNewsResponse(
                    jsonNode.get("location_name").asText(),
                    jsonNode.get("type").asText()
            );

            return Optional.of(analyzeNewsResponse);

        } catch (Exception e) {
            System.out.println("Error in AI service");
            e.printStackTrace();
            return Optional.empty();
        }
    }
}