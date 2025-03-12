package com.example.windsurferweather.entities;

public class WeatherResponse {
    private final String location;
    private final double temperature;
    private final double windSpeed;

    public WeatherResponse(String location, double temperature, double windSpeed) {
        this.location = location;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
    }

    public String getLocation() { return location; }
    public double getTemperature() { return temperature; }
    public double getWindSpeed() { return windSpeed; }
}
