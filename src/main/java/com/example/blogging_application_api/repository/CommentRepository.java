package com.example.blogging_application_api.repository;

import com.example.blogging_application_api.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Integer> {
}
