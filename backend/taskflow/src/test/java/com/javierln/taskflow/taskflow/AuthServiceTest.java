package com.javierln.taskflow.taskflow;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.javierln.taskflow.taskflow.Repository.UserRepository;
import com.javierln.taskflow.taskflow.config.JwtUtil;
import com.javierln.taskflow.taskflow.dto.UserRequestDTO;
import com.javierln.taskflow.taskflow.dto.authentication.AuthenticationRequestDTO;
import com.javierln.taskflow.taskflow.dto.authentication.AuthenticationResponseDTO;
import com.javierln.taskflow.taskflow.entity.UserEntity;
import com.javierln.taskflow.taskflow.service.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterUserExistingEmail() {
        UserRequestDTO userDto = new UserRequestDTO("John Doe", "user@example.com", "password");
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(new UserEntity()));
        assertThrows(Exception.class, () -> authService.registerUser(userDto));

    }

    @Test
    public void registerUser() throws Exception {
        UserRequestDTO userDto = new UserRequestDTO("Jane Dore ", "newuser@example.com", "password");
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        authService.registerUser(userDto);
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    public void testLoginUser_UserNotFound() {
        AuthenticationRequestDTO userDto = new AuthenticationRequestDTO("user@example.com", "password");
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> authService.loginUser(userDto));
    }

    @Test
    public void testLoginUser_Authenticated_ReturnToken() {
        AuthenticationRequestDTO userDto = new AuthenticationRequestDTO("user@example.com", "password");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("user@example.com");
        userEntity.setPassword("hashedPassword"); // Usa el hash si es necesario

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                "user@example.com",
                "hashedPassword",
                Collections.emptyList());

        String generatedToken = "jwt-token";

        // Mock
        when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(userEntity));
        when(jwtUtil.generateToken(userDetails)).thenReturn(generatedToken);

        // Act
        AuthenticationResponseDTO response = authService.loginUser(userDto);

        // Assert
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        verify(userDetailsService, times(1)).loadUserByUsername("user@example.com");
        verify(userRepository, times(1)).findByEmail("user@example.com");
        verify(jwtUtil, times(1)).generateToken(userDetails);

    }

}
