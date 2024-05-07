package com.example.movie.repository;

import com.example.movie.Entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepo extends JpaRepository <Actor, Long> {

    List<Actor> findActorsByMovieId(Long id);
}
