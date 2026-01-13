package com.ntg.JobHunter.domain;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ntg.JobHunter.domain.constant.GenderEnum;
import com.ntg.JobHunter.utils.security.SecurityUtils;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="users")
@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor
public class User{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name" ,length = 100)
    private String name;

    @Column(name = "age" ,length = 3)
    private int age;

    @Column(name = "email" ,length = 50, nullable = false)
    @NotBlank(message = "Email is required")
    @NotNull(message = "Email cannot be null")
    @Email(message="Please provide a valid email address")
    private String email;

    @Column(name = "address" ,length = 200)
    private String address;

    @Column(name = "password" ,length = 200)
    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    private String password;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "refresh_token", columnDefinition = "MEDIUMTEXT")
    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;
    private String updatedBy;


    @PrePersist
    public void handleCreatedAt() {
        this.createdAt = Instant.now();
        this.createdBy = SecurityUtils.getCurrentUserLogin().get();
    }

    @PreUpdate
    public void handleUpdatedAt() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtils.getCurrentUserLogin().get();
    }
}