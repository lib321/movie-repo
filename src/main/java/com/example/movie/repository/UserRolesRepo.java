package com.example.movie.repository;

import com.example.movie.Entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepo extends JpaRepository <UserRoles, Long> {
}
