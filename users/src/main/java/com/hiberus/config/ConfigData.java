package com.hiberus.config;

import com.hiberus.models.User;
import com.hiberus.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConfigData {
    @Bean("ConfigData")
    CommandLineRunner commandLineRunner(UserRepository repositorioUsuario) {
        return args -> {
            User user1 = User.builder()
                    .nombre("Emilio")
                    .apellidos("Fernandez Ruiz")
                    .email("emilio@hiberus.com")
                    .build();

            User user2 = User.builder()
                    .nombre("Lucía")
                    .apellidos("Casas Martín")
                    .email("lucia@hiberus.com")
                    .build();

            User user3 = User.builder()
                    .nombre("Matías")
                    .apellidos("Pérez Mora")
                    .email("matias@hiberus.com")
                    .build();

            repositorioUsuario.saveAll(List.of(user1, user2, user3));
        };
    }
}
