package com.ntg.JobHunter.service;

import com.ntg.JobHunter.domain.User;
import com.ntg.JobHunter.repo.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void handleSaveUser(User user) {
        userRepository.save(user);
    }
    
    public Optional<User> handleGetUser(Long id) {
        return userRepository.findById(id);
    }
    public Optional<User> handleUpdateUser(User user) {
        return Optional.of(userRepository.save(user));
    }
    public User handleGetUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }
}
