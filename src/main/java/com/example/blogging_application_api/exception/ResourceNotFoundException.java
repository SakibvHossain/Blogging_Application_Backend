package com.example.blogging_application_api.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException {
    private Integer fieldValue;

    public ResourceNotFoundException(Integer fieldValue) {
        super(String.format("Resource Not found of the following ID: %d", fieldValue));
        this.fieldValue = fieldValue;
    }
}
