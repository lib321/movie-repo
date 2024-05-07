package com.example.movie.repository;

import com.example.movie.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository <Roles, Long> {
}
