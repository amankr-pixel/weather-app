package com.weatherapp.backend.dto;

public class ForecastDto {

    private String date;
    private double temperature;
    private String description;
    private String icon;

    public ForecastDto(String date, double temperature, String description, String icon){
        this.date = date;
        this.temperature = temperature;
        this.description = description;
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
