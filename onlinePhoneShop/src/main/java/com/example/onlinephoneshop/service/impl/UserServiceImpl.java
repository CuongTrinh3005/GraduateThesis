package com.example.onlinephoneshop.service.impl;

import com.example.onlinephoneshop.dto.UserDTO;
import com.example.onlinephoneshop.entity.Role;
import com.example.onlinephoneshop.entity.User;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.enums.RoleName;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.RoleRepository;
import com.example.onlinephoneshop.repository.UserRepository;
import com.example.onlinephoneshop.service.EmailService;
import com.example.onlinephoneshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.onlinephoneshop.utils.Helper.generatePassword;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmailService emailService;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO findByUsername(String username) {
        return convertToDto(userRepository.findByUsername(username).get());
    }

    @Override
    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByUserName(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(()-> new ResourceNotFoundException(userName + " not found"));
        return Optional.of(user);
    }

    @Override
    public Boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        final String defaultPassword = "1234";
        user.setPassword(encoder.encode(defaultPassword));
        if(user.getGender()==null)
        	user.setGender(true);
        
        return userRepository.save(user);
    }

    @Override
    public UserDTO convertToDto(User user) {
        UserDTO dto = modelMapper.map(user,UserDTO.class);
        dto.setRoleName(getUserRoleAsString(user));
        return dto;
    }

    public String getUserRoleAsString(User user){
        String roles = "";
        Object[] roleArray = user.getRoles().toArray();
        for(int index=0; index<roleArray.length; index++){
            Role role = (Role) roleArray[index];
            String roleName = "";
            if(role.getRoleId() == 1){
                roleName += "User";
            }
            else if(role.getRoleId() == 2){
                roleName += "Admin";
            }
            roles += roleName + " ";
        }
        return roles.trim().replace(" ", ", ");
    }

    @Override
    public User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO,User.class);
        user.setRoles(convertRoleStrToSet(userDTO));
        return user;
    }

    public Set<Role> convertRoleStrToSet(UserDTO userDTO){
        Set<Role> roles = new HashSet<>();
        String strRoles = userDTO.getRoleName();

        if(strRoles != null && !strRoles.equals("")){
            String[] roleStrArr = strRoles.split(", ");
            for(int index=0; index < roleStrArr.length;index++){
                switch (roleStrArr[index].trim().toLowerCase()){
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            }
        }
        else{
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
        return roles;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User updateUser(UserDTO userDTO, String userId) {
        Optional<User> userOpt = findById(userId);
        User existedUser = userOpt.get();
        User user = convertToEntity(userDTO);

        existedUser.setUsername(user.getUsername());
        existedUser.setFullName(user.getFullName());
        existedUser.setGender(user.getGender());
        existedUser.setPhoneNumber(user.getPhoneNumber());
        existedUser.setAddress(user.getAddress());
        existedUser.setEmail(user.getEmail());
        existedUser.setImage(user.getImage());
        existedUser.setBirthday(user.getBirthday());
        existedUser.setRoles(user.getRoles());

        existedUser.setCommonCoef(user.getCommonCoef());
        existedUser.setEntertainCoef(user.getEntertainCoef());
        existedUser.setGamingCoef(user.getGamingCoef());

        return saveUser(existedUser);
    }

    @Override
    public Boolean changePassword(String username, String oldPassword, String newPassword) {
        Boolean isUserExist = userRepository.existsByUsername(username);
        if(!isUserExist)	throw new ResourceNotFoundException(CustomMessages.USER_NOT_FOUND.getDescription());

        User user = userRepository.findByUsername(username).get();
        if(!encoder.matches(oldPassword, user.getPassword())){
            throw new CustomException(CustomMessages.INCORRECT_CURRENT_PASSWORD.getDescription());
        }
        else if(encoder.matches(newPassword, user.getPassword())){
            throw new CustomException(CustomMessages.NEW_PASSWORD_MUST_DIFFERENT_CURRENT_PASSWORD.getDescription());
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }

    @Override
    public Boolean resetPassword(String username) {
        String newPassword;
        try{
            if(username == null || ("").equals(username))
                throw new CustomException("Username must not empty");

            Boolean exist = existByUsername(username);
            if(!exist)	throw new CustomException(CustomMessages.USER_NOT_FOUND.getDescription());

            final int passwordLength = 6;
            newPassword = generatePassword(passwordLength);

            User user = findByUserName(username).get();

            String contentConfirmation = String.format("Your password is reset to %s. "
                    + "Keep it secret for safety!!!", newPassword);

            String to = user.getEmail();
            String subject = "Reset password";
            String text = contentConfirmation;
            // Sending email
            emailService.sendSimpleMessage(to, subject, text);

            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
