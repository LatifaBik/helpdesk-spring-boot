package org.example.helpdeskspringboot.service;

import org.example.helpdeskspringboot.entity.User;
import org.example.helpdeskspringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
