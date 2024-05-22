package com.example.footlooseAPI.services;

import com.example.footlooseAPI.dtos.LoginUserDto;
import com.example.footlooseAPI.dtos.RegisterUserDto;
import com.example.footlooseAPI.entities.*;
import com.example.footlooseAPI.repositories.CartRepository;
import com.example.footlooseAPI.repositories.RoleRepository;
import com.example.footlooseAPI.repositories.UserRepository;
import com.example.footlooseAPI.repositories.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private WishRepository wishRepository;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity signup(RegisterUserDto input) {
        Optional<RoleEntity> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        UserEntity user = new UserEntity();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());
        user = userRepository.save(user);
        CartEntity cart = new CartEntity();
        cart.setOwner(user);
        this.cartRepository.save(cart);
        WishEntity wishList = new WishEntity();
        wishList.setOwner(user);
        this.wishRepository.save(wishList);

        user.setCart(cart);
        user.setWishList(wishList);
        return userRepository.save(user);
    }

    public UserEntity authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    public List<UserEntity> allUsers() {
        List<UserEntity> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}