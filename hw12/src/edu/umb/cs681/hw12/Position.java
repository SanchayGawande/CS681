package edu.umb.cs681.hw12;

import java.util.List;

public record Position(double latitude, double longitude, double altitude) {

    public List<Double> coordinates() {
        return List.of(latitude, longitude, altitude);
    }

    public Position change(double newLat, double newLon, double newAlt) {
        return new Position(newLat, newLon, newAlt);
    }

    public boolean higherAltThan(Position anotherPosition) {
        return this.altitude > anotherPosition.altitude;
    }

    public boolean lowerAltThan(Position anotherPosition) {
        return this.altitude < anotherPosition.altitude;
    }

    public boolean northOf(Position anotherPosition) {
        return this.latitude > anotherPosition.latitude;
    }

    public boolean southOf(Position anotherPosition) {
        return this.latitude < anotherPosition.latitude;
    }
}
