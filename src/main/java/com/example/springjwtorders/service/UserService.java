package com.example.springjwtorders.service;

import com.example.springjwtorders.entity.dto.UserDto;
import com.example.springjwtorders.entity.User;
import com.example.springjwtorders.exception.NotFoundException;
import com.example.springjwtorders.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(u -> !u.isDeleted())
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return toDto(user);
    }

    // Новий метод для профілю
    public UserDto getProfile(User currentUser) {
        return getUserById(currentUser.getId());
    }

    public UserDto createUser(User user) {
        userRepository.save(user);
        return toDto(user);
    }

    public UserDto updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setUsername(updatedUser.getUsername());
        user.setRole(updatedUser.getRole());

        userRepository.save(user);
        return toDto(user);
    }

    // Викликаємо тільки з ID
    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setDeleted(true);
        userRepository.save(user);
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}
