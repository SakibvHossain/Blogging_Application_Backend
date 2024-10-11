package com.example.blogging_application_api.services;


import com.example.blogging_application_api.payload.dtos.CategoryDTO;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    //update
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);
    //delete
    void deleteCategory(Integer categoryId);
    //get by id
    CategoryDTO getCategoryById(Integer categoryId);
    //get all
    List<CategoryDTO> getAllCategories();
    //specific field update
    CategoryDTO specificFieldUpdate(CategoryDTO categoryDTO, Integer categoryId);
}
