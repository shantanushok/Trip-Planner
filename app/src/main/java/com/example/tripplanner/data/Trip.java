package com.example.tripplanner.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "trips")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String destination;
    private String startDate;
    private String endDate;
    private double budget;
    private String startLocation;
    private double startLat;
    private double startLon;
    private double destinationLat;
    private double destinationLon;

    // ✅ Updated constructor including destinationLat and destinationLon
    public Trip(String destination, String startDate, String endDate, double budget,
                String startLocation, double startLat, double startLon,
                double destinationLat, double destinationLon) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.startLocation = startLocation;
        this.startLat = startLat;
        this.startLon = startLon;
        this.destinationLat = destinationLat;
        this.destinationLon = destinationLon;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public double getStartLat() { return startLat; }
    public void setStartLat(double startLat) { this.startLat = startLat; }

    public double getStartLon() { return startLon; }
    public void setStartLon(double startLon) { this.startLon = startLon; }

    public double getDestinationLat() { return destinationLat; }
    public void setDestinationLat(double destinationLat) { this.destinationLat = destinationLat; }

    public double getDestinationLon() { return destinationLon; }
    public void setDestinationLon(double destinationLon) { this.destinationLon = destinationLon; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return id == trip.id &&
                Double.compare(trip.budget, budget) == 0 &&
                Double.compare(trip.startLat, startLat) == 0 &&
                Double.compare(trip.startLon, startLon) == 0 &&
                Double.compare(trip.destinationLat, destinationLat) == 0 &&
                Double.compare(trip.destinationLon, destinationLon) == 0 &&
                Objects.equals(destination, trip.destination) &&
                Objects.equals(startDate, trip.startDate) &&
                Objects.equals(endDate, trip.endDate) &&
                Objects.equals(startLocation, trip.startLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, destination, startDate, endDate, budget, startLocation,
                startLat, startLon, destinationLat, destinationLon);
    }

    @NonNull
    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", budget=" + budget +
                ", startLocation='" + startLocation + '\'' +
                ", startLat=" + startLat +
                ", startLon=" + startLon +
                ", destinationLat=" + destinationLat +
                ", destinationLon=" + destinationLon +
                '}';
    }
}

