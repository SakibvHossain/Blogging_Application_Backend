package com.example.blogging_application_api.payload.dtos;

import com.example.blogging_application_api.entity.Category;
import com.example.blogging_application_api.entity.Comments;
import com.example.blogging_application_api.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private Integer postId;
    private String title;
    private String image;
    private String content;
    private Date addedDate;

    private CategoryDTO category;
    private UserDTO author;
    private Set<CommentDTO> comments = new HashSet<>();
}
