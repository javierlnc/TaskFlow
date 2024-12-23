package com.javierln.taskflow.taskflow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javierln.taskflow.taskflow.entity.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, String> {

}
