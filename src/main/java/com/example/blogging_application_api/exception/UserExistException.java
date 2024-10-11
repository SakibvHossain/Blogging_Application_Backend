package com.example.blogging_application_api.exception;


public class UserExistException extends RuntimeException{
    public UserExistException(String message){
        super(message);
    }
}
