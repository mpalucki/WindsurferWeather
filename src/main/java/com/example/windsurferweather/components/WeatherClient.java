package com.example.windsurferweather.components;

import com.example.windsurferweather.entities.Location;
import com.example.windsurferweather.entities.WeatherApiDataHandler;
import com.example.windsurferweather.entities.WeatherApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class WeatherClient {

    private static final Logger logger = LoggerFactory.getLogger(WeatherClient.class);
    private final RestTemplate restTemplate;

    @Value("${weatherbit.api.key}")
    private String apiKey;

    @Value("${weatherbit.api.url}")
    private String apiUrl;

    public WeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<WeatherApiResponse> getWeatherForLocation(Location location) {
        try {
            logger.info("Executing getWeatherForLocation method");
            String url = String.format("%s?city=%s&key=%s&days=16", apiUrl, location.getName(), apiKey);
            WeatherApiDataHandler response = restTemplate.getForObject(url, WeatherApiDataHandler.class);
            return response.extractWeatherForDate(location.getName());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error fetching weather data", e);
        }
    }
}
