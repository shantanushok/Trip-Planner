package com.example.tripplanner.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int tripId; // Foreign Key to Trip
    private String category;
    private double amount;
    private String date;

    // ✅ Default Constructor (Required for Room)
    public Expense() {
    }

    // ✅ Parameterized Constructor
    public Expense(int tripId, String category, double amount, String date) {
        this.tripId = tripId;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    // ✅ Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTripId() { return tripId; }
    public void setTripId(int tripId) { this.tripId = tripId; } // ✅ Added setter

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
