package com.example.springjwtorders.controller;

import com.example.springjwtorders.entity.dto.UserDto;
import com.example.springjwtorders.entity.User;
import com.example.springjwtorders.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/profile")
    public UserDto getProfile(@RequestAttribute("currentUser") User currentUser) {
        return userService.getProfile(currentUser);
    }

    @PostMapping
    public UserDto createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id);
    }
}
