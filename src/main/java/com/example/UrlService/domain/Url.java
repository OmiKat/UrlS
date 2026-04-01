package com.example.UrlService.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false , name = "originalUrl")
    private String originalUrl;

    @Column(nullable = false, name = "shorty")
    private String shortUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;


}
