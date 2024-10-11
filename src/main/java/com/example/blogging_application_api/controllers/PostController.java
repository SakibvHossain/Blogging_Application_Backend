package com.example.blogging_application_api.controllers;

import com.example.blogging_application_api.payload.ApiResponse;
import com.example.blogging_application_api.payload.PostResponse;
import com.example.blogging_application_api.payload.dtos.PostDTO;
import com.example.blogging_application_api.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("user/{userId}/category/{categoryId}")
    public ApiResponse<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userId, @PathVariable Integer categoryId){
        return new ApiResponse<>(HttpStatus.CREATED,"Post Created", postService.createPost(postDTO,userId,categoryId));
    }

    @GetMapping("postBy/category/{categoryId}")
    public ApiResponse<PostResponse> getAllPostsByCategoryId(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize
    ){
        return new ApiResponse<>(HttpStatus.OK,"All Posts by Category", postService.getPostsByCategory(categoryId,pageNumber - 1,pageSize));
    }

    @GetMapping("postBy/author/{userId}")
    public ApiResponse<List<PostDTO>> getAllPostsByUserId(@PathVariable Integer userId){
        return new ApiResponse<>(HttpStatus.OK,"All Posts by Author", postService.getPostsByUser(userId));
    }

    /*
    In the @GetMapping method, subtract 1 from pageNumber before passing it to
    the service method to ensure that the user-provided pageNumber (1-based) works
    with the zero-based logic of PageRequest.
     */

    // Note: Always remember never name an entity field using underscore because I saw problem while sorting...
    @GetMapping("post/all")
    public ApiResponse<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy
    ) {
        // Subtract 1 from pageNumber for zero-based indexing
        return new ApiResponse<>(HttpStatus.OK, "All Posts", postService.getAllPosts(pageNumber - 1, pageSize,sortBy));
    }

    @GetMapping("post/id/{postId}")
    public ApiResponse<PostDTO> getPostsById(@PathVariable Integer postId){
        return new ApiResponse<>(HttpStatus.OK,"Post by Id:" + postId, postService.getPostById(postId));
    }

    @DeleteMapping("post/delete/{postId}")
    public ApiResponse<String> deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ApiResponse<>(HttpStatus.OK,"Post Deleted!", "Post was deleted with the following Id: " + postId);
    }

    @PutMapping("post/update/{postId}")
    public ApiResponse<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer postId){
        return new ApiResponse<>(HttpStatus.OK,"Post Updated", postService.updatePost(postDTO,postId));
    }

}
