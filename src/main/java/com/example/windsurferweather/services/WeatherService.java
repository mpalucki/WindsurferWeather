package com.example.windsurferweather.services;

import com.example.windsurferweather.components.WeatherClient;
import com.example.windsurferweather.entities.Location;
import com.example.windsurferweather.entities.WeatherApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private final WeatherClient weatherClient;
    private final List<Location> locations;

    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
        this.locations = new ArrayList<>();
        initializeLocations();
    }

    private void initializeLocations() {
        locations.add(new Location("Jastarnia", 54.6961, 18.6789));
        locations.add(new Location("Bridgetown", 13.0975, -59.6184));
        locations.add(new Location("Fortaleza", -3.7172, -38.5433));
        locations.add(new Location("Pissouri", 34.667, 32.7007));
        locations.add(new Location("Le Morne", -20.4623, 57.3302));
    }

    public WeatherApiResponse findBestWindsurfingLocation(String date) {
        LocalDate requestedDate = LocalDate.parse(date);
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusDays(16);

        if (requestedDate.isAfter(maxDate) || requestedDate.isBefore(today)) {
            logger.warn("Requested date {} is out of the valid range (today to 16 days ahead)", date);
            throw new IllegalArgumentException("Date must be within the next 16 days.");
        }

        List<WeatherApiResponse> validForecasts = new ArrayList<>();

        for (Location location : locations) {
            WeatherApiResponse forecastResponse = weatherClient.getWeatherForLocation(location, date);

            if (forecastResponse != null) {
                LocalDate forecastDate = LocalDate.parse(date);
                if (!forecastDate.isBefore(requestedDate) && !forecastDate.isAfter(maxDate)) {
                    double temperature = forecastResponse.getTemp();
                    double windSpeed = forecastResponse.getWind_spd();

                    if (isSuitableForWindsurfing(forecastResponse)) {
                        validForecasts.add(new WeatherApiResponse(location.getName(), temperature, windSpeed));
                    }
                }
            }
        }

        return selectBestLocation(validForecasts);
    }

    private static boolean isSuitableForWindsurfing(WeatherApiResponse response) {
        return response.getWind_spd() >= 5 && response.getWind_spd() <= 18 &&
                response.getTemp() >= 5 && response.getTemp() <= 35;
    }

    private WeatherApiResponse selectBestLocation(List<WeatherApiResponse> forecasts) {
        return forecasts.stream()
                .max(Comparator.comparingDouble(f -> f.getWind_spd() * 3 + f.getTemp()))
                .orElse(null);
    }
}
