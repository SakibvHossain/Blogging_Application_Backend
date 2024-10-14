package com.example.blogging_application_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postId;
    @NotEmpty(message = "Title can not be empty")
    @Size(min = 4, max = 50, message = "Title can be min of 4 and max 50 character")
    @Column(name = "post_title")
    private String title;
    @Column(name = "post_image")
    private String image;
    @NotEmpty(message = "Description can not be empty")
    @Column(name = "post_content")
    private String content;
    @Column(name = "post_date")
    private Date addedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "commentPost", cascade = CascadeType.ALL)
    private Set<Comments> comments = new HashSet<>();
}
