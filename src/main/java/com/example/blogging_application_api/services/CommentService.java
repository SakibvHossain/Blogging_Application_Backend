package com.example.blogging_application_api.services;

import com.example.blogging_application_api.payload.dtos.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO addComment(CommentDTO commentDTO, Integer postId);
    CommentDTO updateComment(CommentDTO commentDTO, Integer commentId);
    CommentDTO getAllComments(Integer postId);
    void deleteComment(Integer commentId);
}
