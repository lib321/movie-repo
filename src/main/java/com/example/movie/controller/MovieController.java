package com.example.movie.controller;

import com.example.movie.Entity.*;
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
        session.setAttribute("currentPage", page);
        return "movie/movie-catalog";
    }

    @GetMapping("/details")
    public String getMovieDetails(@RequestParam Long id,
                                  Model model,
                                  HttpSession session) {
        Movie movie = movieService.getMovieById(id);
        int page = (int) session.getAttribute("currentPage");
        model.addAttribute("movie", movie);
        model.addAttribute("page", page);
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
    public String getMovieDetails(@RequestParam Long id,
                                  Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movie/top10-movie-details";
    }

    @GetMapping("/search")
    public String searchMovies(Model model, HttpSession session,
                               @RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "yearFrom", required = false) Integer yearFrom,
                               @RequestParam(value = "yearTo", required = false) Integer yearTo,
                               @RequestParam(value = "rating", required = false) BigDecimal rating,
                               @RequestParam(value = "actorName", required = false) String actorName,
                               @RequestParam(value = "directorName", required = false) String directorName) {
        List<Movie> movies = movieService.searchMovies(title, yearFrom, yearTo, rating, actorName, directorName);
        session.setAttribute("title", title);
        session.setAttribute("yearFrom", yearFrom);
        session.setAttribute("yearTo", yearTo);
        session.setAttribute("rating", rating);
        session.setAttribute("actorName", actorName);
        session.setAttribute("directorName", directorName);
        model.addAttribute("movies", movies);
        return "movie/movie-search";
    }

    @GetMapping("/search/details")
    public String searchMovieDetails(@RequestParam Long id,
                                     Model model, HttpSession session) {
        Movie movie = movieService.getMovieById(id);
        String title = (String) session.getAttribute("title");
        Integer yearFrom = (Integer) session.getAttribute("yearFrom");
        Integer yearTo = (Integer) session.getAttribute("yearTo");
        BigDecimal rating = (BigDecimal) session.getAttribute("rating");
        String actorName = (String) session.getAttribute("actorName");
        String directorName = (String) session.getAttribute("directorName");
        model.addAttribute("movie", movie);
        model.addAttribute("title", title);
        model.addAttribute("yearFrom", yearFrom);
        model.addAttribute("yearTo", yearTo);
        model.addAttribute("rating", rating);
        model.addAttribute("actorName", actorName);
        model.addAttribute("directorName", directorName);
        return "movie/movie-search-details";
    }
}

