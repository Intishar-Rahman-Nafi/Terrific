package com.example.Terriffic.SearchBot.Service;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

@Service
public class GeoCodingService {
    public Point getLatLonFromLocation(String location) {
        //Call Api Naimmmmmmmmmmmmmm
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        return geometryFactory.createPoint(new Coordinate(90.4125, 23.8103)); //Emnei duita coordinate return korlam
    }
}
