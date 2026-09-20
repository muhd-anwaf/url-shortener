package com.example.url_shortener.entity;

import jakarta.persistence.*;

@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="short_code",unique = true,nullable = false,length = 10)
    private String shortCode;

    @Column(name="original_url",nullable = false)
    private String originalUrl;

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public Long getId() {
        return id;
    }
}
