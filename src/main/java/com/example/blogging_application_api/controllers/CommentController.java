package com.example.blogging_application_api.controllers;

import com.example.blogging_application_api.payload.ApiResponse;
import com.example.blogging_application_api.payload.dtos.CommentDTO;
import com.example.blogging_application_api.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("add/post/{postId}")
    public ApiResponse<CommentDTO> addComment(@Valid @RequestBody CommentDTO commentDTO,@PathVariable Integer postId) {
        return new ApiResponse<>(HttpStatus.OK, "Comment added", commentService.addComment(commentDTO, postId));
    }

    @PostMapping("delete/{id}")
    public ApiResponse<String> deleteComment(@PathVariable Integer id){
        return new ApiResponse<>(HttpStatus.OK, "Comment deleted", "Deleted comment ID: "+id);
    }
}
