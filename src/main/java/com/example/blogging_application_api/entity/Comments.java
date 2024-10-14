package com.example.blogging_application_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    @NotEmpty(message = "Field can not be empty")
    @Size(min = 1, max = 100, message = "Field min of 1 and max 100 character")
    private String commentText;
    private Date commentDate;

    @ManyToOne
    @JoinColumn(name = "comment_description_id")
    private Post commentPost;
}
