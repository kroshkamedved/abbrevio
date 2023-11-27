package com.en.abbrevio.service.parser.impl;

import com.en.abbrevio.exception.ParsingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public final class BoundingBox implements Comparable<BoundingBox> {
    private static final int DEFAULT_BOX_COORDINATES_QUANTITY = 4;
    private final double minX;
    private final double minY;
    private final double maxX;
    private final double maxY;

    public BoundingBox(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public static BoundingBox createFromString(String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        String[] coordinates = string.split("\\s");
        if (coordinates.length != DEFAULT_BOX_COORDINATES_QUANTITY) {
            throw new ParsingException("Invalid \"BorderBox\" attribute. Expected 4 coordinates.");
        }

        try {
            double minX = Double.parseDouble(coordinates[0]);
            double minY = Double.parseDouble(coordinates[1]);
            double maxX = Double.parseDouble(coordinates[2]);
            double maxY = Double.parseDouble(coordinates[3]);

            return new BoundingBox(minX, minY, maxX, maxY);
        } catch (NumberFormatException e) {
            throw new ParsingException("Invalid \"BorderBox\" attribute. Coordinates must be numbers." + e.getMessage());
        }
    }

    @Override
    public int compareTo(BoundingBox o) {
        return (this.minX - o.minX == 0) ? 0 : (this.minX - o.minX) > 0 ? 1 : -1;
    }
}
