package com.example.movie.service;

import com.example.movie.Entity.Actor;
import com.example.movie.repository.ActorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

    @Autowired
    private ActorRepo actorRepo;

    public Page<Actor> getActors(Pageable pageable) {
        return actorRepo.findAll(pageable);
    }
}
