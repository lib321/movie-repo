package com.example.movie.service;

import com.example.movie.Entity.Actor;
import com.example.movie.Entity.Director;
import com.example.movie.Entity.Movie;
import com.example.movie.dto.MovieSearchFilter;
import com.example.movie.repository.ActorRepo;
import com.example.movie.repository.DirectorRepo;
import com.example.movie.repository.MovieRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private ActorRepo actorRepo;

    @Autowired
    private DirectorRepo directorRepo;

    @Autowired
    private EntityManager entityManager;

    public Page<Movie> getMovies(Pageable pageable) {
        return movieRepo.findAll(pageable);
    }

    public Page<Movie> getTopMovies(Pageable pageable) {
        return movieRepo.findByOrderByRatingDesc(pageable);
    }

    public Movie getMovieById(Long id) {
        return movieRepo.findMovieById(id);
    }

    public void deleteMovie(Long id) {
        List<Actor> actors = actorRepo.findActorsByMovieId(id);
        List<Director> directors = directorRepo.findDirectorsByMovieId(id);
        actorRepo.deleteAll(actors);
        directorRepo.deleteAll(directors);
        movieRepo.deleteById(id);
    }

    public void updateMovie(Long id, String name, Integer year, String genre,
                            BigDecimal rate, Integer count, List<String> actorFullName,
                            List<String> directorFullName) {
        Movie movie = movieRepo.findMovieById(id);
        movie.setName(name);
        movie.setYear(year);
        movie.setGenre(genre);
        movie.setRating(rate);
        movie.setCount(count);
        List<Actor> actors = actorRepo.findActorsByMovieId(id);

        for (int i = 0; i < actors.size(); i++) {
            String[] parts = actorFullName.get(i).split("\\s");
            actors.get(i).setName(parts[0]);
            actors.get(i).setSurname(parts[1]);
            actorRepo.save(actors.get(i));
        }

        List<Director> directors = directorRepo.findDirectorsByMovieId(id);
        for (int i = 0; i < directors.size(); i++) {
            String[] parts = directorFullName.get(i).split("\\s");
            directors.get(i).setName(parts[0]);
            directors.get(i).setSurname(parts[1]);
            directorRepo.save(directors.get(i));
        }
        movieRepo.save(movie);
    }

    public void createMovie(String name, Integer year, String genre,
                            BigDecimal rate, Integer count, List<String> actorFullName,
                            List<String> directorFullName) {
        Movie movie = new Movie();
        List<Actor> actors = new ArrayList<>();
        List<Director> directors = new ArrayList<>();

        movie.setName(name);
        movie.setYear(year);
        movie.setGenre(genre);
        movie.setRating(rate);
        movie.setCount(count);
        movieRepo.save(movie);

        for (String fullName : actorFullName) {
            String[] parts = fullName.split("\\s");
            Actor actor = new Actor();
            actor.setName(parts[0]);
            actor.setSurname(parts[1]);
            actor.setMovie(movie);
            actorRepo.save(actor);
            actors.add(actor);
        }

        for (String fullName : directorFullName) {
            String[] parts = fullName.split("\\s");
            Director director = new Director();
            director.setName(parts[0]);
            director.setSurname(parts[1]);
            director.setMovie(movie);
            directorRepo.save(director);
            directors.add(director);
        }

        movie.setActors(actors);
        movie.setDirectors(directors);
    }

    public List<Movie> searchMovies(String name, Integer yearFrom, Integer yearTo,
                                    BigDecimal rate, String actorName, String directorName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> movie = criteriaQuery.from(Movie.class);
        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(movie.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (yearFrom != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(movie.get("year"), yearFrom));
        }
        if (yearTo != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(movie.get("year"), yearTo));
        }
        if (rate != null) {
            predicates.add(criteriaBuilder.equal(movie.get("rating"), rate));
        }
        if (actorName != null) {
            Join<Movie, Actor> actorJoin = movie.join("actors", JoinType.INNER);
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(
                            actorJoin.get("name")), "%" + actorName.toLowerCase() + "%" ));
        }
        if (directorName != null) {
            Join<Movie, Director> directorJoin = movie.join("directors", JoinType.INNER);
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(
                            directorJoin.get("name")), "%" + directorName.toLowerCase() + "%" ));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}

