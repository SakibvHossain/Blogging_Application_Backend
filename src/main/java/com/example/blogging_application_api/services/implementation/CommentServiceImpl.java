package com.example.blogging_application_api.services.implementation;

import com.example.blogging_application_api.entity.Category;
import com.example.blogging_application_api.entity.Comments;
import com.example.blogging_application_api.entity.Post;
import com.example.blogging_application_api.entity.User;
import com.example.blogging_application_api.exception.CategoryNotFoundException;
import com.example.blogging_application_api.exception.ResourceNotFoundException;
import com.example.blogging_application_api.payload.dtos.CommentDTO;
import com.example.blogging_application_api.repository.CommentRepository;
import com.example.blogging_application_api.repository.PostRepository;
import com.example.blogging_application_api.repository.UserRepository;
import com.example.blogging_application_api.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDTO addComment(CommentDTO commentDTO, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        Comments comments = dtoToComments(commentDTO);
        comments.setCommentPost(post);
        comments.setCommentDate(new Date());
        Comments savedComments = commentRepository.save(comments);

        return commentToCommentDTO(savedComments);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, Integer commentId) {
        return null;
    }

    @Override
    public CommentDTO getAllComments(Integer postId) {
        return null;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comments deletedComment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException(commentId)
        );
        commentRepository.delete(deletedComment);
    }

    //CommentDTO to Comment
    public Comments dtoToComments(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comments.class);
    }

    //Comment to CommentDTO
    public CommentDTO commentToCommentDTO(Comments comments) {
        return modelMapper.map(comments, CommentDTO.class);
    }

    //Exception handling
    private Comments findCategoryByIdOrThrow(Integer categoryId) {
        return commentRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
