package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.dto.UserDTO;
import com.example.onlinephoneshop.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAll();
    UserDTO findByUsername(String username);
    User findById();
    User save(User user);
    UserDTO convertToDto(User user);
    User convertToEntity(UserDTO userDTO);
}
