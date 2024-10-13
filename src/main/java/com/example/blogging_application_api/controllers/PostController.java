package com.example.blogging_application_api.controllers;

import com.example.blogging_application_api.config.AppConstants;
import com.example.blogging_application_api.payload.ApiResponse;
import com.example.blogging_application_api.payload.PostResponse;
import com.example.blogging_application_api.payload.dtos.PostDTO;
import com.example.blogging_application_api.services.FileService;
import com.example.blogging_application_api.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    @PostMapping("user/{userId}/category/{categoryId}")
    public ApiResponse<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userId, @PathVariable Integer categoryId){
        return new ApiResponse<>(HttpStatus.CREATED,"Post Created", postService.createPost(postDTO,userId,categoryId));
    }

    @GetMapping("postBy/category/{categoryId}")
    public ApiResponse<PostResponse> getAllPostsByCategoryId(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
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
    //http://localhost:9090/api/post/all?pageNumber=1&pageSize=3
    @GetMapping("post/all")
    public ApiResponse<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_VALUE, required = false) String sortBy
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

    @GetMapping("/post/search/{keyword}")
    public ApiResponse<List<PostDTO>> searchByTitle(@PathVariable String keyword){
        return new ApiResponse<>(HttpStatus.OK,"Post fetched successfully", postService.searchPosts(keyword));
    }

    //Upload image
    @PostMapping("post/upload/image/{postId}")
    public ApiResponse<PostDTO> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId
    ) throws IOException {
        PostDTO postDTO = postService.getPostById(postId);

        String fileName = fileService.uploadImage(path, image);
        postDTO.setImage(fileName);

        return new ApiResponse<>(HttpStatus.OK,"Image file Uploaded", postService.updatePost(postDTO,postId));
    }

    //Download image
    //url: http://localhost:9090/api/post/image/{imageName}
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ApiResponse<Void> downloadImage(
            @PathVariable String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = fileService.downloadImage(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        return null;
    }
}
