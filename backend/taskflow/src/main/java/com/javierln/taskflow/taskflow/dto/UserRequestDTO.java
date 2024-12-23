package com.javierln.taskflow.taskflow.dto;

import java.util.List;

import com.javierln.taskflow.taskflow.entity.TaskEntity;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotEmpty(message = "El nombre no puede estar vacio")
    private String name;
    @NotEmpty(message = "El email no puede estar vacio")
    @Email(message = "El email no es valido")
    private String email;
    @NotEmpty(message = "La contraseña no puede estar vacia")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

}
