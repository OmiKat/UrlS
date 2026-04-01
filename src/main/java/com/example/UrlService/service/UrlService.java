package com.example.UrlService.service;

import com.example.UrlService.domain.Url;
import com.example.UrlService.repo.UrlRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepo repo;
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01223456789";
    private static final int SHORT_CODE_LENGTH = 7;


     public UrlService(UrlRepo repo){
        this.repo = repo;
    }


    public String bigToSmall(String originalUrl){
        Optional<Url> existingUrl = repo.findByOriginalUrl(originalUrl);
        if(existingUrl.isPresent()){
            return existingUrl.get().getShortUrl();
        }

        String shortCode = generateCode(originalUrl);


        //Db store
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortCode);
        url.setCreatedAt(LocalDateTime.now());
        repo.save(url);

        return shortCode;

    }

    private String generateCode(String originalUrl){
        long number  = Math.abs((long) originalUrl.hashCode() * System.currentTimeMillis());
        String code = getBase62(number);

        int attempts = 0;
        while (repo.findByShortUrl(code).isPresent() && attempts < 5) {
            number = (number * 31L + attempts) & Long.MAX_VALUE;
            code = getBase62(number);
            attempts++;
        }
        return code;
    }
    private String getBase62(long number){
         StringBuilder sb =  new StringBuilder();
         while(sb.length() < UrlService.SHORT_CODE_LENGTH){
             sb.append(BASE62.charAt((int) (number%62)));
             number/=62;
         }
         return sb.reverse().toString();
    }
}
