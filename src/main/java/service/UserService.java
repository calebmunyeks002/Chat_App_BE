package com.finchat.backend.service;

import com.finchat.backend.dto.LoginRequest;
import com.finchat.backend.dto.LoginResponse;
import com.finchat.backend.entity.User;
import com.finchat.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public User registerUser(User user) {

        if (emailExists(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (usernameExists(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(

                passwordEncoder.encode(user.getPassword())

        );

        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public LoginResponse login(LoginRequest request) {

        Optional<User> optionalUser =
                userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {

            return new LoginResponse(

                    false,

                    "User not found.",

                    null,

                    null,

                    null

            );

        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(

                request.getPassword(),

                user.getPassword()

        )) {

            return new LoginResponse(

                    false,

                    "Invalid password.",

                    null,

                    null,

                    null

            );

        }
        return new LoginResponse(

                true,

                "Login successful.",

                user.getId(),

                user.getUsername(),

                user.getFullName()

        );

    }
}