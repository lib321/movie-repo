package com.example.movie.controller;

import com.example.movie.Entity.Actor;
import com.example.movie.service.ActorService;
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
@RequestMapping("/actor")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping
    public String getActors(Model model,
                            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 20, Sort.by("name").ascending());
        Page<Actor> actors = actorService.getActors(pageable);
        model.addAttribute("actors", actors);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", actors.getTotalPages());
        return "actor/actor-catalog";
    }
}
