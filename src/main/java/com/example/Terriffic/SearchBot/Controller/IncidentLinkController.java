package com.example.Terriffic.SearchBot.Controller;

import com.example.Terriffic.SearchBot.Service.IncidentParserService;
import com.example.Terriffic.SearchBot.Service.WebScrappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentLinkController {
    @Autowired
    private WebScrappingService webScrapingService;
    @Autowired
    private IncidentParserService incidentParserService;

    @GetMapping("/fetch-links")
    public String fetchLinks() {
        try {
            webScrapingService.fetchIncidentLinks();
            return "Links fetched successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/parse-links")
    public String parseLinks() {
        try {
            incidentParserService.parseUnfetchedLinks();
            return "Links parsed successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
