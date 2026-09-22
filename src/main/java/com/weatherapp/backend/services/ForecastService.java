package com.weatherapp.backend.services;

import com.weatherapp.backend.dto.ForecastDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ForecastService {

    @Value("${weather.forecast.url}")
    private String forecastApiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<ForecastDto> getForecast(String city) {

        String url = forecastApiUrl
                + "?q=" + city
                + "&appid=" + apiKey
                + "&units=metric";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        List<Map<String, Object>> forecastList =
                (List<Map<String, Object>>) response.get("list");

        List<ForecastDto> result = new ArrayList<>();

        for (Map<String, Object> item : forecastList) {

            String dateTime = (String) item.get("dt_txt");

            // Select only forecasts at 12:00 PM
            if (dateTime.contains("12:00:00")) {

                Map<String, Object> main =
                        (Map<String, Object>) item.get("main");

                double temperature =
                        ((Number) main.get("temp")).doubleValue();

                List<Map<String, Object>> weather =
                        (List<Map<String, Object>>) item.get("weather");

                Map<String, Object> weatherInfo =
                        weather.get(0);

                String description =
                        (String) weatherInfo.get("description");

                String icon =
                        (String) weatherInfo.get("icon");

                String date = dateTime.substring(0, 10);

                result.add(
                        new ForecastDto(
                                date,
                                temperature,
                                description,
                                icon
                        )
                );

            }
        }
        return result;
    }

}
