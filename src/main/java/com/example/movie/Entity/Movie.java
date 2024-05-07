package com.example.movie.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer year;

    private String genre;

    @Column(precision = 4, scale = 2)
    private BigDecimal rating;

    private Integer count;

    @OneToMany(mappedBy = "movie")
    private List<Actor> actors;

    @OneToMany(mappedBy = "movie")
    private List<Director> directors;
}
