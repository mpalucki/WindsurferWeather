package com.example.windsurferweather.entities;

import java.util.List;

/*

Class for mapping data to WeatherResponse

*/
public class WeatherApiDataHandler {
    private List<WeatherApiResponse> data;

    public WeatherApiResponse extractWeatherForDate(String date, String location) {
        return data.stream()
                .map(d -> new WeatherApiResponse(location, d.getTemp(), d.getWind_spd()))
                .findFirst()
                .orElse(null);
    }

    public List<WeatherApiResponse> getData() { return data; }
}
