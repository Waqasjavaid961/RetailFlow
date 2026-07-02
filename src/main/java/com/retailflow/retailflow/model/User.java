package com.retailflow.retailflow.model;

import com.retailflow.retailflow.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

// User entity — maps to the "user" table in the database
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

}

