package com.example.blogging_application_api.repository;

import com.example.blogging_application_api.entity.Category;
import com.example.blogging_application_api.entity.Post;
import com.example.blogging_application_api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByAuthor(User author);
    List<Post> findAllByAuthor(User author);
    List<Post> findByCategory(Category category);
    List<Post> findAllByCategory(Category category);
    Page<Post> findByCategory(Category category, Pageable pageable);
    List<Post> findByTitleContaining(String title);
}
