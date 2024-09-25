package com.example.Terriffic.SearchBot.Service;

public class AnalyzeNewsRequest {
    private String title;
    private String news;

    public AnalyzeNewsRequest(String title, String news) {
        this.title = title;
        this.news = news;
    }
}