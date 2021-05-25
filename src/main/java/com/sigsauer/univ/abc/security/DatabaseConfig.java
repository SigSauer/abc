package com.sigsauer.univ.abc.security;


import com.sigsauer.univ.abc.controllers.UserController;
import com.sigsauer.univ.abc.models.users.ERole;
import com.sigsauer.univ.abc.models.users.Role;
import com.sigsauer.univ.abc.models.users.User;
import com.sigsauer.univ.abc.repository.RoleRepository;
import com.sigsauer.univ.abc.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    CommandLineRunner initDataBase(UserRepository userRepository, RoleRepository roleRepository) {
        if (userRepository.existsByUsername("admin"))
            return args -> log.info("Database already exist");
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        Role userRole = new Role(ERole.ROLE_USER);
        User user = new User("admin","Administrator","admin@email.com",
                "$2a$10$QYUj4XyxZJbxoBdrbIuQVuHN7rlkHtKAp3BQnaaGCSnxUrI1hid2O");
        user.setRoles(new HashSet<Role>() {{ add(adminRole); add(userRole); }});

        return args -> log.info("Save initial instances: roles: [" + roleRepository.save(adminRole) + "," +
                roleRepository.save(userRole) + "];\nusers: ["+userRepository.save(user)+"]");
    }
}
