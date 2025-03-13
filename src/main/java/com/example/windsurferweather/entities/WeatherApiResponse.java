package com.example.windsurferweather.entities;

public class WeatherApiResponse {
    private final String city_name;
    private final double temp;
    private final double wind_spd;

    private final String datetime;

    public WeatherApiResponse(String location, double temperature, double windSpeed, String datetime) {
        this.city_name = location;
        this.temp = temperature;
        this.wind_spd = windSpeed;
        this.datetime = datetime;
    }


    public String getCity_name() { return city_name; }
    public double getTemp() { return temp; }
    public double getWind_spd() { return wind_spd; }

    public String getDatetime() {
        return datetime;
    }
}
