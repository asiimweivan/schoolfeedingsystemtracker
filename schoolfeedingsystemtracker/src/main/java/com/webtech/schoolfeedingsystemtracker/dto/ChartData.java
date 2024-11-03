package com.webtech.schoolfeedingsystemtracker.dto;

public class ChartData {
    private String date;
    private long count; // Change from int to long

    // Constructor
    public ChartData(String date, long count) {
        this.date = date;
        this.count = count;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
