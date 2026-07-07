package service;

import dto.LoginRequest;
import dto.LoginResponse;
import entity.User;
import repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        if (!user.getPassword().equals(request.getPassword())) {

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