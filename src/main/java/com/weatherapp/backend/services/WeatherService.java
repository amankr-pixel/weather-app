package com.weatherapp.backend.services;

import com.weatherapp.backend.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.weatherapp.backend.entity.SearchHistory;
import com.weatherapp.backend.repository.SearchHistoryRepository;
import java.time.LocalDateTime;
import com.weatherapp.backend.entity.User;
import com.weatherapp.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;

import java.util.Map;

@Service
public class WeatherService {
    private final SearchHistoryRepository searchHistoryRepository;
    private final UserRepository userRepository;

    public WeatherService(
            SearchHistoryRepository searchHistoryRepository,
            UserRepository userRepository) {

        this.searchHistoryRepository = searchHistoryRepository;
        this.userRepository = userRepository;
    }
    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherResponse getWeather(String city, Authentication authentication){

        String url = apiUrl
                + "?q=" + city
                + "&appid=" + apiKey
                + "&units=metric";

        Map<String, Object> response =
                restTemplate.getForObject(url,Map.class);

        Map<String, Object> main =
                (Map<String, Object>) response.get("main");

        Map<String, Object> wind =
                (Map<String, Object>) response.get("wind");

        var weatherList =
                (java.util.List<Map<String, Object>>) response.get("weather");

        Map<String, Object> weather =
                weatherList.get(0);

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        searchHistoryRepository.save(
                new SearchHistory(
                        city,
                        LocalDateTime.now(),
                        user
                )
        );

        return new WeatherResponse(
                (String) response.get("name"),
                ((Number) main.get("temp")).doubleValue(),
                ((Number) main.get("feels_like")).doubleValue(),
                ((Number) main.get("humidity")).intValue(),
                (String) weather.get("description"),
                ((Number) wind.get("speed")).doubleValue()
        );
    }
}
