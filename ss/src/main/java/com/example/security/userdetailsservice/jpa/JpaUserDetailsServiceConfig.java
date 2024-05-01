package com.example.security.userdetailsservice.jpa;

import com.example.entity.Authority;
import com.example.entity.User;
import com.example.repository.AuthorityRepository;
import com.example.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import java.util.List;
import java.util.Set;


public class JpaUserDetailsServiceConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new JpaUserDetailsService(userRepository);
    }

    @Bean
    public ApplicationRunner initializeUsers(AuthorityRepository authorityRepository,
                                             UserRepository userRepository,
                                             PasswordEncoder passwordEncoder) {
        return args -> {
            boolean usersExist = userRepository.findByUsername("bob@example.com").isPresent();
            if (usersExist) {
                return;
            }

            userRepository.deleteAll();
            authorityRepository.deleteAll();

            Authority roleInstructor = authorityRepository.save(new Authority("ROLE_INSTRUCTOR"));
            Authority roleStudent = authorityRepository.save(new Authority("ROLE_STUDENT"));

            User bob = new User(
                    "bob@example.com",
                    passwordEncoder.encode("password"),
                    Set.of(roleInstructor)
            );

            User jojo = new User(
                    "jojo@example.com",
                    passwordEncoder.encode("password"),
                    Set.of(roleInstructor)
            );

            User alice = new User(
                    "alice@example.com",
                    passwordEncoder.encode("password"),
                    Set.of(roleStudent)
            );

            userRepository.saveAll(List.of(bob, jojo, alice));
        };
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        // Enables Spring Data integration
        return new SecurityEvaluationContextExtension();
    }
}