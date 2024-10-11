package com.example.blogging_application_api.services.implementation;

import com.example.blogging_application_api.entity.User;
import com.example.blogging_application_api.exception.ResourceNotFoundException;
import com.example.blogging_application_api.payload.dtos.UserDTO;
import com.example.blogging_application_api.repository.UserRepository;
import com.example.blogging_application_api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    /*
    A cleaner and more modern approach is constructor injection,
    where you can annotate the constructor with @Autowired (or omit it
    in Spring Boot versions 4.3 and above, as it's implicit for single
    constructors). This also promotes better testability and immutability.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        /*
        Passing value DTO to User because we pass value to DTO first then
        we will pass value on User entity after setting the value to User
        then we will save it after saving the value then pass user to dto
         */
        User user = dtoToUser(userDTO);

//        if (userRepository.findUserByName(user.getName()) != null) {
//            throw new UserExistException("User already exists with the name: "+user.getName());
//        }
        User savedUser = userRepository.save(user);
        return userToUserDTO(savedUser);
    }

    /**
     * @param userDTO
     * @param id
     * @return Updated User
     */
    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User savedUser = userRepository.save(user);
        return userToUserDTO(savedUser);
    }

    /**
     * @param userId
     * @return user by ID
     */
    @Override
    public UserDTO getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(userId)
        );

        return userToUserDTO(user);
    }

    /**
     * @return list of Users
     */
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::userToUserDTO).toList();
    }


    @Override
    public void deleteUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(userId)
        );
        userRepository.delete(user);
    }

    /**
     * @param userId
     * @param userDTO
     * @return Updated Partial User Filed
     */
    @Override
    public UserDTO partialUpdateUser(Integer userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(userId)
        );

        // Update only the fields provided in the userDTO
        if (userDTO.getName() != null) {
            existingUser.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null) {
            existingUser.setPassword(userDTO.getPassword());
        }
        if (userDTO.getAbout() != null) {
            existingUser.setAbout(userDTO.getAbout());
        }

        User savedUser = userRepository.save(existingUser);

        return userToUserDTO(savedUser);
    }


    @Override
    public UserDTO getUserByEmail(String email) {
        //User = this.userRepository.findUserByEmail();
        return null;
    }

    @Override
    public void deleteAllUsers() {
        this.userRepository.deleteAll();
    }

    //Custom DTO to User convert
    public User dtoToUser(UserDTO userDTO) {
//        User user = new User();
//        user.setId(userDTO.getId());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());
//        user.setName(userDTO.getName());
//        user.setAbout(userDTO.getAbout());
        return modelMapper.map(userDTO, User.class);
    }

    //User to DTO convert
    public UserDTO userToUserDTO(User user) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(user.getId());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setPassword(user.getPassword());
//        userDTO.setName(user.getName());
//        userDTO.setAbout(user.getAbout());
        return modelMapper.map(user, UserDTO.class);
    }
}
