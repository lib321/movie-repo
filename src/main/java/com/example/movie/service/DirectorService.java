package com.example.movie.service;

import com.example.movie.Entity.Actor;
import com.example.movie.Entity.Director;
import com.example.movie.repository.DirectorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepo directorRepo;

    public Page<Director> getDirectors(Pageable pageable) {
        return directorRepo.findAll(pageable);
    }
}


