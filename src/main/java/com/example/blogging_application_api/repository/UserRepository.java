package com.example.blogging_application_api.repository;

import com.example.blogging_application_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String username);
}
