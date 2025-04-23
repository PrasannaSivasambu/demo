package com.example.urlshortner.service;


import org.springframework.stereotype.Service;

import com.example.urlshortner.model.Url;
import com.example.urlshortner.repository.UrlRepository;

import org.apache.commons.validator.routines.UrlValidator;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }
   

    public boolean isValidUrl(String url) {
        UrlValidator validator = new UrlValidator(new String[]{"http", "https"});
        return validator.isValid(url);
    }
    

    public String shortenUrl(String originalUrl) {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        Url saved = urlRepository.save(url);

        String shortCode = encode(saved.getId());
        saved.setShortCode(shortCode);
        urlRepository.save(saved);

        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));
        return url.getOriginalUrl();
    }


    private static String encode(Long num) {
        double h=Math.random();
        return Long.toString(num)+Double.toString(h).substring(2);
    }
}
