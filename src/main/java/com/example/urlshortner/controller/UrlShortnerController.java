package com.example.urlshortner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.urlshortner.service.UrlService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.net.URI;

@RestController
public class UrlShortnerController {
    private final UrlService urlService;

    public UrlShortnerController(UrlService urlService) {
        this.urlService = urlService;
    }


     @Operation(summary = "Shorten a valid URL")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Short URL generated",
            content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "400", description = "Invalid URL",
            content = @Content(mediaType = "text/plain"))
    })
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


