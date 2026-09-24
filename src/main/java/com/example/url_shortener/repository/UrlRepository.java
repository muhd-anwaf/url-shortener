package com.example.url_shortener.repository;

import com.example.url_shortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UrlRepository extends JpaRepository<Url,Long> {

    @Query("SELECT u FROM Url u WHERE u.shortCode= :shortCode")
    Optional<Url> findByShortCode(String shortCode);

    @Query("SELECT COUNT(u) >0 FROM Url u WHERE u.shortCode = :shortCode")
    boolean existsByShortCode(String shortCode);
}
