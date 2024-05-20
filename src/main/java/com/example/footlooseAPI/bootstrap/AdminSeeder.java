package com.example.footlooseAPI.bootstrap;

import com.example.footlooseAPI.dtos.RegisterUserDto;
import com.example.footlooseAPI.entities.RoleEntity;
import com.example.footlooseAPI.entities.RoleEnum;
import com.example.footlooseAPI.entities.UserEntity;
import com.example.footlooseAPI.repositories.RoleRepository;
import com.example.footlooseAPI.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminSeeder(
            RoleRepository roleRepository,
            UserRepository  userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setFullName("Super Admin").setEmail("super.admin@email.com").setPassword("123456");

        Optional<RoleEntity> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        UserEntity user = new UserEntity();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}