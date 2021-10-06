package com.example.onlinephoneshop.controller;

import com.example.onlinephoneshop.dto.UserDTO;
import com.example.onlinephoneshop.entity.User;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.payload.request.ChangePasswordRequest;
import com.example.onlinephoneshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public UserDTO getUserByUsername(@RequestParam String username){
        return userService.findByUsername(username);
    }

    @GetMapping("{userId}")
    public UserDTO getUserById(@PathVariable String userId){
        Optional<User> userOptional = userService.findById(userId);
        if(!userOptional.isPresent())
            throw new ResourceNotFoundException(CustomMessages.USER_NOT_FOUND.getDescription());
        return userService.convertToDto(userOptional.get());
    }

    @PutMapping("{userId}")
    public ResponseEntity<User> updateDetails(@Valid @RequestBody UserDTO userDTO, @PathVariable String userId) {
        return new ResponseEntity<User>(userService.updateUser(userDTO, userId), HttpStatus.OK);
    }

    @PostMapping("change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordReq){
        Boolean success = userService.changePassword(changePasswordReq.getUsername(), changePasswordReq.getCurrentPassword()
                , changePasswordReq.getNewPassword());

        return (success)? ResponseEntity.ok().body(CustomMessages.CHANGE_PASSWORD_SUCCESS.getDescription())
                : ResponseEntity.ok().body(CustomMessages.CHANGE_PASSWORD_FAILED.getDescription());
    }
}
