package com.example.Terriffic.ScrapperBot.Model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

import java.sql.Timestamp;

@Entity
@Table(name = "NewsPage")
public class NewsPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
