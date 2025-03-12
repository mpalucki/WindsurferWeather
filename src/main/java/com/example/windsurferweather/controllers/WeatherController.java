package com.example.windsurferweather.controllers;

import com.example.windsurferweather.entities.WeatherResponse;
import com.example.windsurferweather.services.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/best-location")
    public ResponseEntity<WeatherResponse> getBestLocation(@RequestParam String date) {
        return ResponseEntity.ok(weatherService.findBestWindsurfingLocation(date));
    }
}
