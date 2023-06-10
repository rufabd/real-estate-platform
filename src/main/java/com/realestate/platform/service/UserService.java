package com.realestate.platform.service;

import com.realestate.platform.dto.UserDto;
import com.realestate.platform.jwt.JwtService;
import com.realestate.platform.model.User;
import com.realestate.platform.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String registerUser(UserDto userDto) {
        User user = User.builder()
                        .email(userDto.getEmail())
                        .dob(userDto.getDob())
                        .firstName(userDto.getFirstName())
                        .surname(userDto.getSurname())
                        .phone(userDto.getPhone())
                        .role(userDto.getRole()).build();

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        log.info("New user with id {} has been successfully created", user.getId());
        return jwtService.generateToken(user.getEmail());
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String loginAndGetToken(UserDto userDto) {
        if(userDto.getEmail() == null || userDto.getPassword() == null) {
            throw new UsernameNotFoundException("Email or password is empty");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));

        if(authentication.isAuthenticated()) {
            log.info("Generated token is {}", jwtService.generateToken(userDto.getEmail()));
            return jwtService.generateToken(userDto.getEmail());
        } else {
            throw new UsernameNotFoundException("The user can't be authenticated!");
        }
    }

    public Boolean authenticate(String header) {
        String token = header.replace("Bearer ", "");
        log.info("Authenticate - token {}", token);
        return jwtService.validateToken(token);
    }


}
