package com.example.url_shortener.service;

import com.example.url_shortener.dto.CreateUrlResponse;
import com.example.url_shortener.dto.UrlPageResponse;
import com.example.url_shortener.dto.UrlStatsResponse;
import com.example.url_shortener.entity.Url;
import com.example.url_shortener.exception.ShortUrlNotFoundException;
import com.example.url_shortener.repository.UrlRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }


    public CreateUrlResponse createShortUrl(String originalUrl){

        String shortCode ;
        do{
            shortCode = generateShortCode();
        }while(urlRepository.existsByShortCode(shortCode));

        Url url = new Url();
        url.setShortCode(shortCode);
        url.setOriginalUrl(originalUrl);
        url.setCreatedAt(LocalDateTime.now());
        url.setClickCount(0);

        urlRepository.save(url);

        String shortUrl = "http://localhost:8080/" + shortCode;


        return new CreateUrlResponse(shortCode,shortUrl,originalUrl,url.getCreatedAt(),url.getClickCount());
    }


    private String generateShortCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            shortCode.append(characters.charAt(index));
        }
        return shortCode.toString();
    }

    public String getOriginalUrl(String shortCode){
        Optional<Url> url = urlRepository.findByShortCode(shortCode);
        if(url.isEmpty()){
            throw new ShortUrlNotFoundException("Short URL not found");
        }
        url.get().setClickCount(url.get().getClickCount()+1);
        urlRepository.save(url.get());
        return url.get().getOriginalUrl();
    }

    public UrlStatsResponse createUrlStats(String shortCode) {
        Optional<Url> url = urlRepository.findByShortCode(shortCode);
        if(url.isEmpty()){
            throw new ShortUrlNotFoundException("Short URL not found");
        }
        String originalUrl = url.get().getOriginalUrl();
        LocalDateTime createdAt = url.get().getCreatedAt();
        int clickCount = url.get().getClickCount();
        return new UrlStatsResponse(shortCode,originalUrl,createdAt,clickCount);

    }

    public UrlPageResponse getAllUrls(int page , int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Url> urlPage = urlRepository.findAll(pageable);
        List<UrlStatsResponse> urls = urlPage.getContent().stream().map(url->
                new UrlStatsResponse
                        (url.getShortCode(),url.getOriginalUrl(),
                                url.getCreatedAt(),url.getClickCount())).toList();
        return new UrlPageResponse
                (urls,urlPage.getNumber(),urlPage.getSize(),
                        urlPage.getTotalElements(),urlPage.getTotalPages());
    }
}
