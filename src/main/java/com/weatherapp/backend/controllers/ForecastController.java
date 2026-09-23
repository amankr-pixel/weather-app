package com.weatherapp.backend.controllers;

import com.weatherapp.backend.dto.ForecastDto;
import com.weatherapp.backend.services.ForecastService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/forecast")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService){
        this.forecastService = forecastService;
    }

    @GetMapping
    public List<ForecastDto> getForecast(
            @RequestParam String city
    ){
        return forecastService.getForecast(city);
    }
}
