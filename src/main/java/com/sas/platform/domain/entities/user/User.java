package com.sas.platform.domain.entities.user;

import java.util.UUID;
import java.time.ZonedDateTime;

public class User {
    private UUID id;
    private String name;
    private String email;
    private String passwordHash;
    private UserRole role;
    private ZonedDateTime registerDate;

    public User(UUID id, String name, String email, String passwordHash, UserRole role, ZonedDateTime registerDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.registerDate = registerDate;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public UserRole getRole() {
        return this.role;
    }

    public ZonedDateTime getRegisterDate() {
        return this.registerDate;
    }
}
