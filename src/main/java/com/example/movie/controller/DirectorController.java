package com.example.movie.controller;

import com.example.movie.Entity.Actor;
import com.example.movie.Entity.Director;
import com.example.movie.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/director")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping
    public String getActors(Model model,
                            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 20, Sort.by("name").ascending());
        Page<Director> directors = directorService.getDirectors(pageable);
        model.addAttribute("directors", directors);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", directors.getTotalPages());
        return "director/director-catalog";
    }
}
