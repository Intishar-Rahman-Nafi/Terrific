package com.example.Terriffic.Incident.Model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;

@Converter(autoApply = true)
public class PointConverter implements AttributeConverter<Point, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(Point attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString().getBytes();
    }

    @Override
    public Point convertToEntityAttribute(byte[] dbData) {
        if (dbData == null) {
            return null;
        }
        String wkt = new String(dbData);
        try {
            return new GeometryFactory().createPoint(new org.locationtech.jts.io.WKTReader().read(wkt).getCoordinate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
