package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT u
            FROM User u
            WHERE u.email = :username
            """)
    Optional<User> findByUsername(String username);

    @Query("""
            SELECT u
            FROM User u
            WHERE u.apiKey = :apiKey
            """)
    Optional<User> findByApiKey(String apiKey);
}
