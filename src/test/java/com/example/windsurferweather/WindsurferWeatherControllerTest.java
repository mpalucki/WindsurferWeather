package com.example.windsurferweather;

import com.example.windsurferweather.controllers.WeatherController;
import com.example.windsurferweather.entities.WeatherApiResponse;
import com.example.windsurferweather.services.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@WebMvcTest(WeatherController.class)
public class WindsurferWeatherControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(WindsurferWeatherControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherService weatherService;

    @Test
    void testGetBestLocation() throws Exception {
        String date = "2025-03-15";
        WeatherApiResponse mockResponse = new WeatherApiResponse("Jastarnia", 20.0, 12.0, date);
        when(weatherService.findBestWindsurfingLocation(date)).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/best-location")
                        .param("date", date)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city_name").value("Jastarnia"));
    }

    @Test
    void testGetBestLocation_NotFound() throws Exception {
        String date = "2025-03-15";
        when(weatherService.findBestWindsurfingLocation(date)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/best-location")
                        .param("date", date)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetBestLocation_DateOutOfRange() throws Exception {
        logger.info("Executing testGetBestLocation_DateOutOfRange");
        String date = LocalDate.now().plusDays(17).toString();

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/best-location")
                        .param("date", date)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
