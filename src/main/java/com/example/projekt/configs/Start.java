package com.example.projekt.configs;

import com.example.projekt.models.User;
import com.example.projekt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(basePackages = "com.example.projekt")
public class Start {


    public Start(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        User user = new User();
        user.setEmail("admin@admin");
        user.setPassword(passwordEncoder.encode("123"));
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);

        User user1 = new User();
        user1.setEmail("user@user");
        user1.setPassword(passwordEncoder.encode("123"));
        user1.setRole("ROLE_USER");
        userRepository.save(user1);
    }
}
