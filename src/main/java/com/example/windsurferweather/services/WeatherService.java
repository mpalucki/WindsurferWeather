package com.example.windsurferweather.services;

import com.example.windsurferweather.components.WeatherClient;
import com.example.windsurferweather.entities.Location;
import com.example.windsurferweather.entities.WeatherApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${weatherbit.api.maxWindSpeed}")
    private double maxWindSpeed;

    @Value("${weatherbit.api.minWindSpeed}")
    private double minWindSpeed;

    @Value("${weatherbit.api.maxTemp}")
    private double maxTemp;

    @Value("${weatherbit.api.minTemp}")
    private double minTemp;

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
            List<WeatherApiResponse> forecastResponses = weatherClient.getWeatherForLocation(location);

            if (!forecastResponses.isEmpty()) {
                for(WeatherApiResponse response : forecastResponses) {
                    LocalDate forecastDate = LocalDate.parse(response.getDatetime());
                    if (!forecastDate.isBefore(requestedDate) && !forecastDate.isAfter(requestedDate.plusDays(7))) {
                        double temperature = response.getTemp();
                        double windSpeed = response.getWind_spd();

                        if (isSuitableForWindsurfing(response)) {
                            validForecasts.add(new WeatherApiResponse(location.getName(), temperature, windSpeed, response.getDatetime()));
                        }
                    }
                }
            }
        }
        return selectBestLocation(validForecasts);
    }

    private boolean isSuitableForWindsurfing(WeatherApiResponse response) {
        return response.getWind_spd() >= minWindSpeed && response.getWind_spd() <= maxWindSpeed &&
                response.getTemp() >= minTemp && response.getTemp() <= maxTemp;
    }

    private WeatherApiResponse selectBestLocation(List<WeatherApiResponse> forecasts) {
        return forecasts.stream()
                .max(Comparator.comparingDouble(f -> (f.getWind_spd() * 3) + f.getTemp()))
                .orElse(null);
    }
}
