package com.example.footlooseAPI.services;

import com.example.footlooseAPI.dtos.RegisterUserDto;
import com.example.footlooseAPI.entities.RoleEntity;
import com.example.footlooseAPI.entities.RoleEnum;
import com.example.footlooseAPI.entities.UserEntity;
import com.example.footlooseAPI.dtos.UserModel;
import com.example.footlooseAPI.repositories.RoleRepository;
import com.example.footlooseAPI.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> allUsers() {
        List<UserEntity> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public UserEntity createAdministrator(RegisterUserDto input) {
        Optional<RoleEntity> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) {
            return null;
        }

        UserEntity user = new UserEntity();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());

        return userRepository.save(user);
    }

    public Integer getUserRoleId(UserModel userModel){
        Optional<UserEntity> user = this.userRepository.findById(userModel.getId());

        if(user.isPresent()){
            return user.get().getRole().getId();
        }

        return 0;
    }
}