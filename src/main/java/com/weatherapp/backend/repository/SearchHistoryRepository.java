package com.weatherapp.backend.repository;

import com.weatherapp.backend.entity.SearchHistory;
import com.weatherapp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository
        extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findByUserOrderBySearchedAtDesc(User user);
}