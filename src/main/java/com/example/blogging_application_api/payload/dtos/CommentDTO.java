package com.example.blogging_application_api.payload.dtos;


import com.example.blogging_application_api.entity.Post;
import com.example.blogging_application_api.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class CommentDTO {
    private Integer commentId;
    private String commentText;
    private Date commentDate;
}
