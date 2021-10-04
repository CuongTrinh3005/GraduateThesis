package com.example.onlinephoneshop.service.impl;

import com.example.onlinephoneshop.dto.UserDTO;
import com.example.onlinephoneshop.entity.Role;
import com.example.onlinephoneshop.entity.User;
import com.example.onlinephoneshop.enums.RoleName;
import com.example.onlinephoneshop.repository.RoleRepository;
import com.example.onlinephoneshop.repository.UserRepository;
import com.example.onlinephoneshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO findByUsername(String username) {
        return convertToDto(userRepository.findByUsername(username).get());
    }

    @Override
    public User findById() {
        return null;
    }

    @Override
    public User save(User user) {
        final String defaultPassword = "1234";
        user.setPassword(encoder.encode(defaultPassword));
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
}
