package com.example.blogging_application_api.User;

import com.example.blogging_application_api.entity.User;
import com.example.blogging_application_api.repository.UserRepository;
import com.example.blogging_application_api.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void getUserByUsername(){
        User user = userRepository.findUserByName("Khadija");
        Assertions.assertEquals("Khadija",user.getName());
    }
}
