package com.example.url_shortener.controller;

import com.example.url_shortener.dto.CreateUrlResponse;
import com.example.url_shortener.dto.CreateUrlRequest;
import com.example.url_shortener.dto.UrlPageResponse;
import com.example.url_shortener.dto.UrlStatsResponse;
import com.example.url_shortener.service.UrlService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/urls")
@Validated
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping
    public CreateUrlResponse createShortUrl(@Valid @RequestBody CreateUrlRequest request){
        return urlService.createShortUrl(request.getOriginalUrl());
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location",originalUrl)
                .build();
    }

    @GetMapping("/{shortCode}/stats")
    public UrlStatsResponse createUrlStats(@PathVariable String shortCode){
        return urlService.createUrlStats(shortCode);
    }

    @GetMapping
    public UrlPageResponse getAllUrls(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page must be 0 or greater") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1 , message = "size must be atleast 1")
            @Max(value = 101 ,message = "size >100") int size,
            @RequestParam(defaultValue = "createdAt") @Pattern(regexp = "createdAt|clickCount",message ="sortBy must be createdAt or clickCount") String sortBy,
            @RequestParam(defaultValue = "desc") @Pattern(regexp = "(?i)asc|desc", message = "Direction must be asc or desc") String direction)
        {
        return urlService.getAllUrls(page,size,sortBy,direction);
    }



}
