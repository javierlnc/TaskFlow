package com.javierln.taskflow.taskflow.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javierln.taskflow.taskflow.Repository.UserRepository;
import com.javierln.taskflow.taskflow.config.JwtUtil;
import com.javierln.taskflow.taskflow.dto.UserRequestDTO;
import com.javierln.taskflow.taskflow.dto.authentication.AuthenticationRequestDTO;
import com.javierln.taskflow.taskflow.dto.authentication.AuthenticationResponseDTO;
import com.javierln.taskflow.taskflow.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public void registerUser(UserRequestDTO userDto) throws Exception {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new Exception("El email: " + userDto.getEmail() + " ya se encuentra registrado");
        }
        UserEntity user = UserEntity.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .enabled(true)
                .accountLocked(false)
                .build();
        userRepository.save(user);
    }

    public AuthenticationResponseDTO loginUser(AuthenticationRequestDTO userDto) {

        UserDetails userDetails = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        authenticatedUser(userDetails.getUsername(), userDetails.getPassword());
        String token = jwtUtil.generateToken(userDetails);
        return buildResponse(userDetails, token);

    }

    private AuthenticationResponseDTO buildResponse(UserDetails userDetails, String token) {
        return AuthenticationResponseDTO.builder()
                .id(userDetails.getUsername())
                .name(userDetails.getUsername())
                .email(userDetails.getUsername())
                .token(token)
                .build();

    }

    private void authenticatedUser(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new RuntimeException("Contraseña incorrectos");
        }
    }

}
