package com.weatherapp.backend.entity;

import jakarta.persistence.*;

@Entity
public class FavoriteCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public FavoriteCity() {
    }

    public FavoriteCity(String city, User user) {
        this.city = city;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}