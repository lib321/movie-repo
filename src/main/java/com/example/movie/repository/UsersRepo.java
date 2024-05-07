package com.example.movie.repository;

import com.example.movie.Entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UsersRepo extends JpaRepository <Users, Long> {

    Optional<Users> findByLogin(String login);
}
