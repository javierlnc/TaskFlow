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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    private static final AtomicInteger counter = new AtomicInteger(1);
    private static final int MAX_COUNTER = 99999;

    public void generateId() {

        String date = new SimpleDateFormat("YYMMdd").format(new Date());
        String uniquePart = String.format("%05d", getNextCounterValue());
        this.id = date + uniquePart;
    }

    private int getNextCounterValue() {
        int nextValue = counter.getAndIncrement();

        if (nextValue > MAX_COUNTER) {
            counter.set(1);
            nextValue = counter.get();
        }

        return nextValue;
    }

}
