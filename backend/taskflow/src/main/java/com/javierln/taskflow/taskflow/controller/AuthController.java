package com.javierln.taskflow.taskflow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.javierln.taskflow.taskflow.dto.UserRequestDTO;
import com.javierln.taskflow.taskflow.dto.authentication.AuthenticationRequestDTO;
import com.javierln.taskflow.taskflow.dto.authentication.AuthenticationResponseDTO;
import com.javierln.taskflow.taskflow.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequestDTO userDto) {
        try {
            authService.registerUser(userDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> postMethodName(@RequestBody AuthenticationRequestDTO userDto) {
        AuthenticationResponseDTO response = authService.loginUser(userDto);
        return ResponseEntity.ok(response);
    }

}
