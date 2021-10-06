package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.dto.UserDTO;
import com.example.onlinephoneshop.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getAll();
    UserDTO findByUsername(String username);
    Optional<User> findById(String userId);
    User saveUser(User user);
    UserDTO convertToDto(User user);
    User convertToEntity(UserDTO userDTO);
    void delete(User user);
    User updateUser(UserDTO userDTO, String username);
    Boolean changePassword(String username, String oldPassword, String newPassword);
    Boolean resetPassword(String username);
    Optional<User> findByUserName(String userName);
    Boolean existByUsername(String username);
    Boolean existByEmail(String email);
}
