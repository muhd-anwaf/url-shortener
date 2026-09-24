package com.example.url_shortener.service;

import com.example.url_shortener.dto.CreateUrlResponse;
import com.example.url_shortener.entity.Url;
import com.example.url_shortener.exception.ShortUrlNotFoundException;
import com.example.url_shortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        urlRepository.save(url);
        String shortUrl = "http://localhost:8080/" + shortCode;
        return new CreateUrlResponse(shortCode,shortUrl,originalUrl);
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
        return url.get().getOriginalUrl();
    }

}
