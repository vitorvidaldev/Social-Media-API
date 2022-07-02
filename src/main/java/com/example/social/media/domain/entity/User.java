package com.example.social.media.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document("user")
public class User {
    @Size(max = 14)
    @Pattern(regexp = "[a-zA-Z\\d]*")
    @Indexed(unique = true)
    private String username;
    private LocalDateTime signupDate;
    @MongoId(FieldType.STRING)
    private UUID userId;

    public User() {
    }

    public User(String username) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.signupDate = LocalDateTime.now();
    }
}
