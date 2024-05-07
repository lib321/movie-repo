package com.example.movie.controller;

import com.example.movie.Entity.*;
import com.example.movie.dto.MovieSearchFilter;
import com.example.movie.service.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping()
    public String getMovies(Model model,
                            HttpSession session,
                            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 20, Sort.by("name").ascending());
        Page<Movie> movies = movieService.getMovies(pageable);
        model.addAttribute("movies", movies);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", movies.getTotalPages());
        session.setAttribute("page", page);
        return "movie/movie-catalog";
    }

    @GetMapping("/details")
    public String getMovieDetails(@RequestParam Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movie/movie-details";
    }

    @PostMapping("/delete")
    public String deleteMovie(@RequestParam Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movie";
    }

    @GetMapping("/update")
    public String updateMovie(@RequestParam Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movie/movie-update";
    }

    @PostMapping("/update")
    public String updateMovieAction(@RequestParam Long id, @RequestParam String name,
                                    @RequestParam Integer year, @RequestParam String genre,
                                    @RequestParam BigDecimal rate, @RequestParam Integer count,
                                    @RequestParam List<String> actorFullName,
                                    @RequestParam List<String> directorFullName) {
        movieService.updateMovie(id, name, year, genre, rate, count, actorFullName, directorFullName);
        return "redirect:/movie/details?id=" + id;
    }

    @GetMapping("/create")
    public String createMovie() {
        return "movie/movie-create";
    }

    @PostMapping("/create")
    public String createMovieAction(@RequestParam String name, @RequestParam Integer year,
                                    @RequestParam String genre, @RequestParam BigDecimal rate,
                                    @RequestParam Integer count, @RequestParam List<String> actorFullName,
                                    @RequestParam List<String> directorFullName) {
        movieService.createMovie(name, year, genre, rate, count, actorFullName, directorFullName);
        return "redirect:/movie";
    }

    @GetMapping("/top10")
    public String getTop10Movies(Model model) {
        Pageable pageable = PageRequest.ofSize(10);
        Page<Movie> movies = movieService.getTopMovies(pageable);
        model.addAttribute("movies", movies);
        return "movie/top10-movie";
    }

    @GetMapping("/top10/details")
    public String getMovieDetailsTop10(@RequestParam Long id,
                                       Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movie/top10-movie-details";
    }

    @GetMapping("/search")
    public String searchMovies(Model model, HttpSession session, MovieSearchFilter filter) {

        List<Movie> movies = movieService.searchMovies(filter.getTitle(), filter.getYearFrom(), filter.getYearTo(),
                filter.getRating(), filter.getActorName(), filter.getDirectorName());
        session.setAttribute("filter", filter);
        model.addAttribute("movies", movies);
        model.addAttribute("filter", filter);
        return "movie/movie-search";
    }

    @GetMapping("/search/details")
    public String searchMovieDetails(@RequestParam Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movie/movie-search-details";
    }
}

