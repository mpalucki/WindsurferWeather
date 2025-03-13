package com.example.windsurferweather.controllers;

import com.example.windsurferweather.components.WeatherClient;
import com.example.windsurferweather.entities.WeatherApiResponse;
import com.example.windsurferweather.services.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/best-location")
    public ResponseEntity<WeatherApiResponse> getBestLocation(@RequestParam String date) {
        logger.info("Executing getBestLocation");
        WeatherApiResponse response = weatherService.findBestWindsurfingLocation(date);
        if (response == null) {
            logger.error("No suitable windsurfing location found for the given date: {}", date);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No suitable windsurfing location found for the given date.");
        }
        return ResponseEntity.ok(response);
    }
}
