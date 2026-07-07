package controller;

import entity.User;
import service.UserService;

import org.springframework.web.bind.annotation.*;

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