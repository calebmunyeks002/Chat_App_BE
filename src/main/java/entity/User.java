package com.finchat.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;

    @Column(name = "online")
    private boolean online;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
}