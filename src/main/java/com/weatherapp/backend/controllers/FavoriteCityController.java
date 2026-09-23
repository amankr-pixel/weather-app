package com.weatherapp.backend.controllers;

import com.weatherapp.backend.entity.FavoriteCity;
import com.weatherapp.backend.entity.User;
import com.weatherapp.backend.repository.FavoriteCityRepository;
import com.weatherapp.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteCityController {

    private final FavoriteCityRepository favoriteCityRepository;
    private final UserRepository userRepository;

    public FavoriteCityController(
            FavoriteCityRepository favoriteCityRepository,
            UserRepository userRepository) {

        this.favoriteCityRepository = favoriteCityRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> addFavorite(
            @RequestBody FavoriteCity favoriteCity,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean alreadyExists =
                favoriteCityRepository
                        .existsByCityIgnoreCaseAndUser(
                                favoriteCity.getCity(),
                                user
                        );

        if (alreadyExists) {
            return ResponseEntity.badRequest()
                    .body("City is already in favorites");
        }

        favoriteCity.setUser(user);

        FavoriteCity savedFavorite =
                favoriteCityRepository.save(favoriteCity);

        return ResponseEntity.ok(savedFavorite);
    }

    @GetMapping
    public List<FavoriteCity> getFavorites(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return favoriteCityRepository.findByUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        FavoriteCity favorite =
                favoriteCityRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Favorite not found"));

        if (!favorite.getUser().getId()
                .equals(user.getId())) {

            return ResponseEntity.status(403).build();
        }

        favoriteCityRepository.delete(favorite);

        return ResponseEntity.noContent().build();
    }
}