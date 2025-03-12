package com.example.windsurferweather.services;

import com.example.windsurferweather.components.WeatherClient;
import com.example.windsurferweather.entities.Location;
import com.example.windsurferweather.entities.WeatherResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {
    private final WeatherClient weatherClient;
    private final List<Location> locations = List.of(
            new Location("Jastarnia", 54.6961, 18.6789),
            new Location("Bridgetown", 13.0975, -59.6184),
            new Location("Fortaleza", -3.7172, -38.5433),
            new Location("Pissouri", 34.667, 32.7007),
            new Location("Le Morne", -20.4623, 57.3302)
    );

    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public WeatherResponse findBestWindsurfingLocation(String date) {
        return locations.stream()
                .map(location -> weatherClient.getWeatherForLocation(location, date))
                .filter(WeatherService::isSuitableForWindsurfing)
                .max(WeatherService::compareByWindsurfingScore)
                .orElse(null);
    }

    private static boolean isSuitableForWindsurfing(WeatherResponse response) {
        return response.getWindSpeed() >= 5 && response.getWindSpeed() <= 18 &&
                response.getTemperature() >= 5 && response.getTemperature() <= 35;
    }

    private static int compareByWindsurfingScore(WeatherResponse w1, WeatherResponse w2) {
        return Double.compare(w1.getWindSpeed() * 3 + w1.getTemperature(),
                w2.getWindSpeed() * 3 + w2.getTemperature());
    }
}
