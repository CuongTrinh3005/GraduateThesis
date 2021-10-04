package com.example.onlinephoneshop.controller;

import com.example.onlinephoneshop.dto.UserDTO;
import com.example.onlinephoneshop.entity.User;
import com.example.onlinephoneshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public UserDTO getAllUsers(@RequestParam String username){
        return userService.findByUsername(username);
    }

    @PostMapping
    public ResponseEntity<User> insertUser(@Valid @RequestBody User user){
        User newUser = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(newUser.getUsername())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
