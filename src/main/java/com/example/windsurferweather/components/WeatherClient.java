package com.example.windsurferweather.components;

import com.example.windsurferweather.entities.Location;
import com.example.windsurferweather.entities.WeatherApiData;
import com.example.windsurferweather.entities.WeatherResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient {
    private final RestTemplate restTemplate;
    private final String apiKey = "YOUR_WEATHERBIT_API_KEY";
    private final String apiUrl = "https://api.weatherbit.io/v2.0/forecast/daily";

    public WeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeatherForLocation(Location location, String date) {
        String url = String.format("%s?lat=%f&lon=%f&key=%s", apiUrl, location.getLatitude(), location.getLongitude(), apiKey);
        WeatherApiData response = restTemplate.getForObject(url, WeatherApiData.class);
        return response != null ? response.extractWeatherForDate(date, location.getName()) : null;
    }
}
