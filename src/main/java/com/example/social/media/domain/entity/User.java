package com.example.social.media.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class User {
    @Size(max = 14)
    @Pattern(regexp = "[a-zA-Z\\d]*")
    private String username;
    private LocalDateTime signupDate;
    private UUID userId;

    public User() {
    }

    public User(String username) {
        this.username = username;
        this.userId = UUID.randomUUID();
        this.signupDate = LocalDateTime.now();
    }
}
