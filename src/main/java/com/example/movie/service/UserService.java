package com.example.movie.service;

import com.example.movie.Entity.UserRoles;
import com.example.movie.Entity.Users;
import com.example.movie.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepo userRepo;

    public Users getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return userRepo.findByLogin(authentication.getName()).orElse(null);
    }

    public boolean isAdmin() {
        if (getCurrentUser() != null) {
            return getCurrentUser().getUserRoles().stream()
                    .anyMatch(userRole -> userRole.getRoles().getName().equals("admin"));
        }
        return false;
    }
}
