package com.example.url_shortener.controller;


import com.example.url_shortener.dto.CreateUrlRequest;
import com.example.url_shortener.service.UrlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping
    public String createShortUrl(@RequestBody CreateUrlRequest request){
        return urlService.createShortUrl(request.getOriginalUrl());
    }

}
