package com.example.blogging_application_api.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDTO {
    private Integer category_id;
    private String category_title;
    private String category_description;
}
