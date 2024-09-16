package com.example.Terriffic.Incident.Model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;

import java.util.Arrays;

@Converter(autoApply = true)
public class PointConverter implements AttributeConverter<Point, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(Point attribute) {
        if (attribute == null) {
            return null;
        }
        WKBWriter writer = new WKBWriter();
        return  writer.write(attribute);
    }

    @Override
    public Point convertToEntityAttribute(byte[] dbData) {
        if (dbData == null) {
            return null;
        }
        WKBReader reader = new WKBReader();
        try {
            return (Point) reader.read(dbData);
        } catch (ParseException e) {
            throw new RuntimeException("Error converting WKB to Point", e);
        }
    }
}