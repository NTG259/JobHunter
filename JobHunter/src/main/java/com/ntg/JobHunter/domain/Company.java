package com.ntg.JobHunter.domain;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ntg.JobHunter.utils.security.SecurityUtils;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "companies")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter
public class Company{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name" ,length = 100, nullable = false)
    @JsonProperty("name")
    @NotBlank(message = "Name can't be blank")
    @NotNull(message = "Name can't be null")
    private String name;

    @Column(name = "description" , columnDefinition = "MEDIUMTEXT")
    @JsonProperty("description")
    private String description;

    @Column(name = "address")
    @JsonProperty("address")
    private String address;

    @Column(name = "logo")
    @JsonProperty("logo")
    private String logo;

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