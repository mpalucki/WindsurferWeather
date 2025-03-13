package com.example.windsurferweather;

import com.example.windsurferweather.controllers.WeatherController;
import com.example.windsurferweather.entities.WeatherApiResponse;
import com.example.windsurferweather.services.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@WebMvcTest(WeatherController.class)
public class WindsurferWeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private WeatherService weatherService;

    @Test
    void testGetBestLocation() throws Exception {
        String date = "2025-06-15";
        WeatherApiResponse mockResponse = new WeatherApiResponse("Jastarnia", 20.0, 12.0);
        when(weatherService.findBestWindsurfingLocation(date)).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/best-location")
                        .param("date", date)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Jastarnia"));
    }

    @Test
    void testGetBestLocation_NotFound() throws Exception {
        String date = "2025-06-15";
        when(weatherService.findBestWindsurfingLocation(date)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/best-location")
                        .param("date", date)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
