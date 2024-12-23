package com.javierln.taskflow.taskflow.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.javierln.taskflow.taskflow.Repository.UserRepository;
import com.javierln.taskflow.taskflow.dto.UserRequestDTO;
import com.javierln.taskflow.taskflow.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserRequestDTO userDto) throws Exception {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new Exception("El email: " + userDto.getEmail() + " ya se encuentra registrado");
        }
        UserEntity user = UserEntity.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                .enabled(true)
                .accountLocked(false)
                .build();
        userRepository.save(user);
    }

    public UserDetailsService userDetailsService() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'userDetailsService'");
    }

}
