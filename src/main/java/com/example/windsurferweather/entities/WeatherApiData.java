package com.example.windsurferweather.entities;

import java.util.List;

public class WeatherApiData {
    private List<WeatherResponse> data;

    public WeatherResponse extractWeatherForDate(String date, String location) {
        return data.stream()
                .filter(d -> d.getLocation().equals(date))
                .map(d -> new WeatherResponse(location, d.getTemperature(), d.getWindSpeed()))
                .findFirst()
                .orElse(null);
    }

    public List<WeatherResponse> getData() { return data; }
}
