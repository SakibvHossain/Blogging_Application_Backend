package com.example.blogging_application_api.services.implementation;

import com.example.blogging_application_api.entity.Category;
import com.example.blogging_application_api.entity.Post;
import com.example.blogging_application_api.entity.User;
import com.example.blogging_application_api.exception.CategoryNotFoundException;
import com.example.blogging_application_api.exception.ResourceNotFoundException;
import com.example.blogging_application_api.payload.dtos.PostDTO;
import com.example.blogging_application_api.repository.CategoryRepository;
import com.example.blogging_application_api.repository.PostRepository;
import com.example.blogging_application_api.repository.UserRepository;
import com.example.blogging_application_api.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * @param postDTO
     * @return Post
     */
    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(userId)
        );

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException(categoryId)
        );

        Post post = postDTOToPost(postDTO);
        post.setImage("default.png"); //we will update later if needed
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setAuthor(user);
        Post savedPost = postRepository.save(post);
        return postToPostDTO(savedPost);
    }

    /**
     * @param postDTO
     * @return
     */
    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post getPost = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        getPost.setTitle(postDTO.getTitle());
        getPost.setContent(postDTO.getContent());
        getPost.setImage(postDTO.getImage());
        getPost.setAddedDate(new Date());

        Post savedPost = postRepository.save(getPost);
        return postToPostDTO(savedPost);
    }
    /**
     * @param postId
     */
    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        postRepository.delete(post);
    }

    /**
     * @param postId
     * @return PostDTO
     */
    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        return postToPostDTO(post);
    }

    /**
     * @return PostDTO
     */
    @Override
    public List<PostDTO> getAllPosts(Integer pageNumber, Integer pageSize) {
        // Ensure pageNumber is not negative
        Pageable pageable = PageRequest.of(Math.max(pageNumber, 0), pageSize);
        Page<Post> pageOfPosts = postRepository.findAll(pageable);
        List<Post> postList = pageOfPosts.getContent();
        return postList.stream().map(this::postToPostDTO).toList();
    }

    /**
     * @param categoryId
     * @return
     */
    @Override
    public List<PostDTO> getPostsByCategory(Integer categoryId) {
        Category getCategoryById = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException(categoryId)
        );

        List<Post> postList = postRepository.findAllByCategory(getCategoryById);
        return postList.stream().map(this::postToPostDTO).toList();
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<PostDTO> getPostsByUser(Integer userId) {
        User getUserById = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(userId)
        );

        List<Post> postList = postRepository.findByAuthor(getUserById);

        return postList.stream().map(this::postToPostDTO).toList();
    }

    /**
     * @param keyword
     * @return
     */
    @Override
    public List<PostDTO> searchPosts(String keyword) {
        return List.of();
    }

    //post to postDTO
    public PostDTO postToPostDTO(Post post){
        return modelMapper.map(post, PostDTO.class);
    }
    //postDTO to post
    public Post postDTOToPost(PostDTO postDTO){
        return modelMapper.map(postDTO, Post.class);
    }
}
