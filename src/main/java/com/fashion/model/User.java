package com.fashion.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // явне вказання імені таблиці
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // Роль користувача (наприклад: ROLE_USER, ROLE_ADMIN)
    private String role;
}