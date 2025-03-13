package com.example.windsurferweather.entities;

import java.util.List;
import java.util.stream.Collectors;

/*

Class for mapping data to WeatherResponse

*/
public class WeatherApiDataHandler {
    private List<WeatherApiResponse> data;

    public List<WeatherApiResponse> extractWeatherForDate(String location) {
        return data.stream()
                .map(d -> new WeatherApiResponse(location, d.getTemp(), d.getWind_spd(), d.getDatetime()))
                .collect(Collectors.toList());
    }
    public List<WeatherApiResponse> getData() { return data; }
}
