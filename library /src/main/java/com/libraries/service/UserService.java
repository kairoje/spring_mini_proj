package com.libraries.service;

import com.libraries.model.UserModel;
import com.libraries.repository.UserRepository;
import com.libraries.security.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserModel findUserByEmailAddress(String emailAddress) {
        return userRepository.findUserByEmailAddress(emailAddress);
    }
}
