package com.sigsauer.univ.abc.security;


import com.sigsauer.univ.abc.models.clients.NaturalClient;
import com.sigsauer.univ.abc.models.communication.Email;
import com.sigsauer.univ.abc.models.communication.Product;
import com.sigsauer.univ.abc.models.users.ERole;
import com.sigsauer.univ.abc.models.users.Role;
import com.sigsauer.univ.abc.models.users.User;
import com.sigsauer.univ.abc.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.HashSet;

@Configuration
public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    CommandLineRunner initDataBase(UserRepository userRepository, RoleRepository roleRepository,
                                   ProductRepository productRepository, NaturalClientRepository ncRepository,
                                   EmailRepository emailRepository) {
        if (userRepository.existsByUsername("admin"))
            return args -> log.info("Database already exist");
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        Role userRole = new Role(ERole.ROLE_USER);
        User user = new User("admin", "Administrator", "admin@email.com",
                "$2a$10$QYUj4XyxZJbxoBdrbIuQVuHN7rlkHtKAp3BQnaaGCSnxUrI1hid2O");
        user.setRoles(new HashSet<Role>() {{
            add(adminRole);
        }});
        Product defProduct = new Product("def", "default", "default description", user);

        NaturalClient client = new NaturalClient("380991234567", "Тестовий Тест Тестович", true, LocalDate.parse("1990-02-13"), "3839302332", "000123123", "3214");
        Email email = new Email(user, client, "TEST", "Test test test");

        return args -> log.info("Save initial instances: roles: [{}, {}, {}, {}, {}, {}]; users: [{}]; products: [{}], users: [{}], emails: [{}]",
                roleRepository.save(userRole),
                roleRepository.save(adminRole),
                roleRepository.save(new Role(ERole.ROLE_MANAGER)),
                roleRepository.save(new Role(ERole.ROLE_RISK)),
                roleRepository.save(new Role(ERole.ROLE_COLLECTOR)),
                roleRepository.save(new Role(ERole.ROLE_MARKETING)),
                userRepository.save(user),
                productRepository.save(defProduct),
                ncRepository.save(client),
                emailRepository.save(email));
    }
}
