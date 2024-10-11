package com.example.blogging_application_api.repository;

import com.example.blogging_application_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
