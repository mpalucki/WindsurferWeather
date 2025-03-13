package com.example.windsurferweather;

import com.example.windsurferweather.components.WeatherClient;
import com.example.windsurferweather.entities.Location;
import com.example.windsurferweather.entities.WeatherApiResponse;
import com.example.windsurferweather.services.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class WindsurferWeatherServiceTest {

    @Mock
    private WeatherClient weatherClient;

    @InjectMocks
    private WeatherService weatherService;
    @Test
    void testFindBestWindsurfingLocation() {
        String date = "2025-06-15";
        Location testLocation = new Location("Test Beach", 10.0, 20.0);
        WeatherApiResponse mockResponse = new WeatherApiResponse("Test Beach", 25.0, 10.0);

        when(weatherClient.getWeatherForLocation(any(Location.class), eq(date))).thenReturn(mockResponse);
        WeatherApiResponse bestLocation = weatherService.findBestWindsurfingLocation(date);
        assertNotNull(bestLocation);
        assertEquals("Test Beach", bestLocation.getCity_name());
    }

}
