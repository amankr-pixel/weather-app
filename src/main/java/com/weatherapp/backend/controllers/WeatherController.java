package com.weatherapp.backend.controllers;

import com.weatherapp.backend.dto.WeatherResponse;
import com.weatherapp.backend.services.WeatherService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService){
        this.weatherService=weatherService;
    }

    @GetMapping("/api/weather")
    public WeatherResponse getWeather(
            @RequestParam String city,
            Authentication authentication) {

        return weatherService.getWeather(
                city,
                authentication
        );
    }

}
