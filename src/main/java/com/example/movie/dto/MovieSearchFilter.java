package com.example.movie.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
public class MovieSearchFilter {
    private String title;
    private Integer yearFrom;
    private Integer yearTo;
    private BigDecimal rating;
    private String actorName;
    private String directorName;
}
