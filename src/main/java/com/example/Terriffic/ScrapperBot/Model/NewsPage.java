package com.example.Terriffic.ScrapperBot.Model;

import com.example.Terriffic.SearchBot.Model.NewsAgency;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

import java.sql.Timestamp;

@Entity
@Table(name = "NewsPages")
public class NewsPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NewsAgency newsAgency;

    private String title;
    private String link;

    @Lob
    private String content;
}
