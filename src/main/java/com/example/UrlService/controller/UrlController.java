package com.example.UrlService.controller;

import com.example.UrlService.domain.Url;
import com.example.UrlService.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service){
        this.service = service;
    }

    @PostMapping("/short")
    public ResponseEntity<String> urlShort(@RequestBody Url url){
        if(url.getOriginalUrl() == null || url.getOriginalUrl().isBlank()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String bigUrl = url.getOriginalUrl();
        String swatyUrl = service.bigToSmall(bigUrl);
        return new ResponseEntity<>(swatyUrl, HttpStatus.OK);
    }

}
