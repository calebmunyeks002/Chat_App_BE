package com.finchat.backend.controller;

import com.finchat.backend.entity.User;
import com.finchat.backend.service.UserService;
import com.finchat.backend.dto.LoginRequest;
import com.finchat.backend.dto.LoginResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){

        this.userService = userService;

    }

    @GetMapping
    public List<User> getUsers(){

        return userService.getAllUsers();

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        LoginResponse response =
                userService.login(request);

        if (response.isSuccess()) {

            return ResponseEntity.ok(response);

        }

        return ResponseEntity
                .status(401)
                .body(response);

    }

    @GetMapping("/{id}")
    public Optional<User> getUser(
            @PathVariable Long id){

        return userService.getUser(id);

    }

    @PostMapping("/register")
    public User register(
            @RequestBody User user){

        return userService.registerUser(user);

    }

    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User user){

        user.setId(id);

        return userService.updateUser(user);

    }

    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable Long id){

        userService.deleteUser(id);

    }

}