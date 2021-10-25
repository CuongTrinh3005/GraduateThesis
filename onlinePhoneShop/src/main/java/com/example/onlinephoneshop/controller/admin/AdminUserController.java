package com.example.onlinephoneshop.controller.admin;

import com.example.onlinephoneshop.dto.UserDTO;
import com.example.onlinephoneshop.entity.User;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/admin/users")
public class AdminUserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAll().stream().map(userService::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<User> insertUser(@Valid @RequestBody UserDTO userDTO){
        User newUser = userService.saveUser(userService.convertToEntity(userDTO));
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(newUser.getUsername())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable String userId){
        Optional<User> userOptional = userService.findById(userId);
        if(!userOptional.isPresent())
            throw new ResourceNotFoundException(CustomMessages.USER_NOT_FOUND.getDescription());

        User user = userOptional.get();
        if (user.getOrders().size() == 0 && user.getRatings().size() == 0)
            userService.delete(user);
        else
            throw new CustomException(CustomMessages.NOT_DELETE_USER.getDescription());
    }
}
