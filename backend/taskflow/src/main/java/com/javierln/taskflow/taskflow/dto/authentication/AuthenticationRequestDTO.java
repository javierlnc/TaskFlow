package com.javierln.taskflow.taskflow.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthenticationRequestDTO {
    @NotEmpty(message = "El email no puede estar vacio")
    @Email(message = "El email no es valido")
    private String email;
    @NotEmpty(message = "La contraseña no puede estar vacia")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

}
