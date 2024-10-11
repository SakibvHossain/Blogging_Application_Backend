package com.example.blogging_application_api.services;

import com.example.blogging_application_api.payload.dtos.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO, Integer userId);
    UserDTO getUserById(Integer userId);
    List<UserDTO> getAllUsers();
    void deleteUserById(Integer userId);
    UserDTO partialUpdateUser(Integer userId, UserDTO userDTO);
    UserDTO getUserByEmail(String email);
    void deleteAllUsers();
}
