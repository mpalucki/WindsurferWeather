package com.example.windsurferweather.controllers;

import com.example.windsurferweather.entities.WeatherResponse;
import com.example.windsurferweather.services.WeatherService;
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
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/best-location")
    public ResponseEntity<WeatherResponse> getBestLocation(@RequestParam String date) {
        WeatherResponse response = weatherService.findBestWindsurfingLocation(date);
        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No suitable windsurfing location found for the given date.");
        }
        return ResponseEntity.ok(response);
    }
}
