package com.example.urlshortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urlshortner.model.Url;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortCode(String shortCode);
}
