package com.raul.demo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.raul.demo.domain.User;
import com.raul.demo.respositories.UserRepository;

@Configuration
@Profile("test")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void startDB() {
        User u1 = new User(null, "João", "joao@mail.com", "123");
        User u2 = new User(null, "Maria", "maria@mail.com", "123");

        repository.saveAll(List.of(u1, u2));
    }
}