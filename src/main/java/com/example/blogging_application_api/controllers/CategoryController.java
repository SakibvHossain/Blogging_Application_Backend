package com.example.blogging_application_api.controllers;

import com.example.blogging_application_api.payload.ApiResponse;
import com.example.blogging_application_api.payload.dtos.CategoryDTO;
import com.example.blogging_application_api.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //create
    @PostMapping("create")
    public ApiResponse<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        return new ApiResponse<>(HttpStatus.CREATED,"New Category Created", categoryService.addCategory(categoryDTO));
    }
    //update
    @PutMapping("update/{categoryId}")
    public ApiResponse<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable Integer categoryId){
        return new ApiResponse<>(HttpStatus.OK,"Category Updated", categoryService.updateCategory(categoryDTO,categoryId));
    }
    //delete
    @DeleteMapping("delete/{categoryId}")
    public ApiResponse<String> deleteCategory(@PathVariable Integer categoryId){
        categoryService.deleteCategory(categoryId);
        return new ApiResponse<>(HttpStatus.OK,"Category deleted","Deleted Category ID: "+categoryId);
    }
    //getAll
    @GetMapping("byId/{categoryId}")
    public ApiResponse<CategoryDTO> getCategoryById(@PathVariable Integer categoryId){
        return new ApiResponse<>(HttpStatus.OK,"Category found",categoryService.getCategoryById(categoryId));
    }
    //getById
    @GetMapping("all")
    public ApiResponse<List<CategoryDTO>> getAllCategory(){
        return new ApiResponse<>(HttpStatus.ACCEPTED,"List of Category",categoryService.getAllCategories());
    }
    //modify specific field
    @PatchMapping("specific/{categoryId}")
    public ApiResponse<CategoryDTO> updateSpecificCategory(@PathVariable Integer categoryId,@Valid @RequestBody CategoryDTO categoryDTO){
        return new ApiResponse<>(HttpStatus.OK,"Field Updated", categoryService.specificFieldUpdate(categoryDTO, categoryId));
    }
}
