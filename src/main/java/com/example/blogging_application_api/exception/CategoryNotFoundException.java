package com.example.blogging_application_api.exception;

public class CategoryNotFoundException extends RuntimeException{
    private Integer fieldValue;

    public CategoryNotFoundException(Integer fieldValue) {
        super(String.format("Category not found at the following ID: %d",fieldValue));
        this.fieldValue = fieldValue;
    }
}
