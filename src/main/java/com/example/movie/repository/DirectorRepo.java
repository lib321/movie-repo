package com.example.movie.repository;

import com.example.movie.Entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectorRepo extends JpaRepository <Director, Long> {

    List<Director> findDirectorsByMovieId(Long id);
}
