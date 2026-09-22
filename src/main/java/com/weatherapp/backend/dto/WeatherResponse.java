package com.weatherapp.backend.dto;

public class WeatherResponse {
    private String city;
    private double temperature;
    private double feelsLike;
    private int humidity;
    private String description;
    private double windSpeed;

    public WeatherResponse(String city, double temperature,
                           double feelsLike, int humidity, String description,
                           double windSpeed){
        this.city=city;
        this.temperature=temperature;
        this.feelsLike=feelsLike;
        this.humidity=humidity;
        this.description=description;
        this.windSpeed=windSpeed;

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
