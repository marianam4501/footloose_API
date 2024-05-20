package com.example.footlooseAPI.controllers;

import com.example.footlooseAPI.dtos.RegisterUserDto;
import com.example.footlooseAPI.entities.UserEntity;
import com.example.footlooseAPI.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserEntity> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> allUsers() {
        List <UserEntity> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null); // Invalidar la autenticación
            return ResponseEntity.ok("Logout exitoso");
        } else {
            return ResponseEntity.badRequest().body("No hay una sesión activa para cerrar sesión");
        }
    }
}