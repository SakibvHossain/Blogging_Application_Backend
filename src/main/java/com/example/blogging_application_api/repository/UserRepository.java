package com.example.blogging_application_api.repository;

import com.example.blogging_application_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
    User findUserByName(String name);
}
