package com.example.demo.Controller;



import com.example.demo.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlShortenerController {
    private final UrlService urlService;

    public UrlShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl) {
        try {
            
            if (!urlService.isValidUrl(originalUrl)) {
                return ResponseEntity.badRequest().body("Invalid URL");
            }
            String shortCode = urlService.shortenUrl(originalUrl);
            String shortUrl = "http://localhost:8080/" + shortCode;
            return ResponseEntity.ok(shortUrl);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(302).location(URI.create(originalUrl)).build();
    }
}

