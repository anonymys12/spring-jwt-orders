package com.example.springjwtorders.entity.dto;
import lombok.Data;
import com.example.springjwtorders.entity.Role;

@Data
public class UserDto {
    private Long id;
    private String username;
    private Role role;
}
