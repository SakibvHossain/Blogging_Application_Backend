package com.example.blogging_application_api.security;

import com.example.blogging_application_api.entity.User;
import com.example.blogging_application_api.exception.ResourceNotFoundException;
import com.example.blogging_application_api.exception.UserExistException;
import com.example.blogging_application_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(() -> new UserExistException(username));
        System.out.println("Username: " + username);
        System.out.println("Password: " + user.getPassword());
        System.out.println("Roles: " + user.getAuthorities());
        return user;
    }
}
