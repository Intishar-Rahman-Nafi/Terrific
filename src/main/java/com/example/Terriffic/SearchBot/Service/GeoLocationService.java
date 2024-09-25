package com.example.Terriffic.SearchBot.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeoLocationService {

    @Value("${google.geocoding.api.key}")
    private String googleGeocodingApiKey;

    public Map<String, Double> getLocationCoordinates(String locationName) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address={locationName}&key={apiKey}";

        Map<String, String> params = new HashMap<>();
        params.put("locationName", locationName);
        params.put("apiKey", googleGeocodingApiKey);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);

        JsonObject jsonResponse = JsonParser.parseString(response.getBody()).getAsJsonObject();
        JsonArray results = jsonResponse.getAsJsonArray("results");

        if (results.size() > 0) {
            JsonObject location = results.get(0).getAsJsonObject().getAsJsonObject("geometry").getAsJsonObject("location");
            double latitude = location.get("lat").getAsDouble();
            double longitude = location.get("lng").getAsDouble();

            Map<String, Double> coordinates = new HashMap<>();
            coordinates.put("latitude", latitude);
            coordinates.put("longitude", longitude);
            return coordinates;
        } else {
            throw new IllegalArgumentException("No location found for the given input: " + locationName);
        }
    }
}