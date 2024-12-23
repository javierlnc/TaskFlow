package com.javierln.taskflow.taskflow.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.javierln.taskflow.taskflow.enums.PriorityEnum;
import com.javierln.taskflow.taskflow.enums.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tasks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TaskEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private PriorityEnum priority;
    private StatusEnum status;
    private boolean isCompleted;
    private Long userId;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    private static final AtomicInteger counter = new AtomicInteger(1);

    public void generateId() {
        String date = new SimpleDateFormat("YYMMdd").format(new Date());
        String uniquePart = String.format("%04d", counter.getAndIncrement());
        this.id = date + uniquePart;
    }

}
