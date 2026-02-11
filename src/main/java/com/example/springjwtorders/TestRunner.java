package com.example.springjwtorders;

import com.example.springjwtorders.entity.Role;
import com.example.springjwtorders.entity.User;
import com.example.springjwtorders.service.AuthService;
import com.example.springjwtorders.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestRunner implements CommandLineRunner {

    private final UserService userService;
    private final AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        // Створюємо користувача
        User user = User.builder()
                .username("testuser")
                .password("password")
                .role(Role.USER)
                .build();

        userService.register(user);

        // Генеруємо JWT
        String token = authService.login("testuser", "password");
        System.out.println("JWT Token: " + token);

        // Перевірка профілю
        User profile = userService.getProfile(user.getId());
        System.out.println("Profile username: " + profile.getUsername());
    }
}
