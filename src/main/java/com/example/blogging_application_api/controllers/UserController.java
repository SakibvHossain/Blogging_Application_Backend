package com.example.blogging_application_api.controllers;


import com.example.blogging_application_api.payload.ApiResponse;
import com.example.blogging_application_api.payload.dtos.UserDTO;
import com.example.blogging_application_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Post (Custom response)
    @PostMapping("createUser")
    public ApiResponse<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ApiResponse<>(HttpStatus.CREATED,"User have been created!",createdUser);
    }

    //Put
    //In build response
//    @PutMapping("/updateUser/{userID}")
//    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO,@PathVariable Integer userID){
//        UserDTO userUpdated = userService.updateUser(userDTO, userID);
//        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
//    }

    //Custom Response
    @PutMapping("/updateUser/{userID}")
    public ApiResponse<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Integer userID){
        UserDTO userUpdated = userService.updateUser(userDTO, userID);
        return new ApiResponse<>(HttpStatus.OK,"User Updated Successfully",userUpdated);
    }

    //Get All Users
    //Using ResponseEntity (in build api response class)
//    @GetMapping("all")
//    public ResponseEntity<List<UserDTO>> getAllUsers(){
//        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
//    }

    //Using ApiResponse (Custom api response class)
    @GetMapping("all")
    public ApiResponse<List<UserDTO>> getAllUsers(){
        List<UserDTO> allUsers = userService.getAllUsers();
        return new ApiResponse<>(HttpStatus.ACCEPTED,"User fetched Successfully",allUsers);
    }

    @PatchMapping("update/specific/{userID}")
    public ApiResponse<UserDTO> updateSpecificUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Integer userID){
        return new ApiResponse<>(HttpStatus.OK,"Field Updated", userService.partialUpdateUser(userID,userDTO));
    }

    //Delete
    @DeleteMapping("deleteUser/{userID}")
    public ApiResponse<String> deleteUser(@PathVariable Integer userID){
        userService.deleteUserById(userID);
        return new ApiResponse<>(HttpStatus.OK,"User have deleted Successfully","User ID: "+userID);
    }

    //Get Single User
    @GetMapping("{userID}")
    public ApiResponse<UserDTO> getUser(@PathVariable Integer userID){
        return new ApiResponse<>(HttpStatus.OK,"User "+userID+" Fetched successfully",userService.getUserById(userID));
    }

}
