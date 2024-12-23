package com.javierln.taskflow.taskflow.dto;

import java.util.List;

import com.javierln.taskflow.taskflow.entity.TaskEntity;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String name;
    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<TaskEntity> tasks;
    private boolean enabled;
    private boolean accountLocked;

}
