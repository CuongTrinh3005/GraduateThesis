package com.example.onlinephoneshop.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.onlinephoneshop.entity.Role;
import com.example.onlinephoneshop.entity.User;
import com.example.onlinephoneshop.enums.RoleName;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.payload.request.LoginRequest;
import com.example.onlinephoneshop.payload.request.SignupRequest;
import com.example.onlinephoneshop.payload.response.JwtResponse;
import com.example.onlinephoneshop.payload.response.MessageResponse;
import com.example.onlinephoneshop.repository.RoleRepository;
import com.example.onlinephoneshop.repository.UserRepository;
import com.example.onlinephoneshop.security.jwt.JwtUtils;
import com.example.onlinephoneshop.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.onlinephoneshop.utils.Helper.verifyPhoneNumber;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	final private AuthenticationManager authenticationManager;

	final private UserRepository userRepository;

	final private RoleRepository roleRepository;

	final private PasswordEncoder encoder;

	final private JwtUtils jwtUtils;

	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
						  RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		// TODO, authenticate when login
		// Username, pass from client
		// com.nashtech.rookies.security.WebSecurityConfig.configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
		// authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		// on this step, we tell to authenticationManager how we load data from
		// database
		// and the password encoder
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		// if go there, the user/password is correct
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// generate jwt to return to client
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getUserId(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new CustomException("Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new CustomException("Email is already in use!");
		}

		// Create new user's account
		if(!verifyPhoneNumber(signUpRequest.getPhoneNumber())){
			throw new CustomException("Phone number is invalid!");
		}
		else if(signUpRequest.getPhoneNumber().length()<8 || signUpRequest.getPhoneNumber().length()>14){
			throw new CustomException("Phone number length is in range 8 - 14!");
		}
		
		User user = new User(signUpRequest.getUsername(), signUpRequest.getFullName(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getPhoneNumber());

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role.toLowerCase()) {
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
			});
		}

		user.setRoles(roles);
		if(user.getGender()==null)
			user.setGender(true);
		
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}