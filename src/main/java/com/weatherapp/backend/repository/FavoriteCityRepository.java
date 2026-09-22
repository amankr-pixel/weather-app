package com.weatherapp.backend.repository;

import com.weatherapp.backend.entity.FavoriteCity;
import com.weatherapp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteCityRepository
    extends JpaRepository<FavoriteCity, Long> {

    boolean existsByCityIgnoreCase(String city);

    List<FavoriteCity> findByUser(User user);

    boolean existsByCityIgnoreCaseAndUser(String city, User user);
}