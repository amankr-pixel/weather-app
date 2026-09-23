package com.weatherapp.backend.controller;

import com.weatherapp.backend.entity.SearchHistory;
import com.weatherapp.backend.entity.User;
import com.weatherapp.backend.repository.SearchHistoryRepository;
import com.weatherapp.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SearchHistoryController {

    private final SearchHistoryRepository searchHistoryRepository;
    private final UserRepository userRepository;

    public SearchHistoryController(
            SearchHistoryRepository searchHistoryRepository,
            UserRepository userRepository) {

        this.searchHistoryRepository = searchHistoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/history")
    public List<SearchHistory> getHistory(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return searchHistoryRepository
                .findByUserOrderBySearchedAtDesc(user);
    }

    @DeleteMapping("/api/history")
    public ResponseEntity<Void> clearHistory(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<SearchHistory> history =
                searchHistoryRepository
                        .findByUserOrderBySearchedAtDesc(user);

        searchHistoryRepository.deleteAll(history);

        return ResponseEntity.noContent().build();
    }
}