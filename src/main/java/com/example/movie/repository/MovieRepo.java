package com.example.movie.repository;

import com.example.movie.Entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepo extends JpaRepository<Movie, Long> {

    Movie findMovieById(Long id);

    Page<Movie> findByOrderByRatingDesc(Pageable pageable);
}
