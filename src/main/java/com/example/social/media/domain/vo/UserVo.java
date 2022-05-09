package com.example.social.media.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserVo {
    private String username;
    private UUID userId;
    private LocalDateTime signupDate;

    public UserVo() {
    }

    public UserVo(UUID userId, String username, LocalDateTime signupDate) {
        this.username = username;
        this.userId = userId;
        this.signupDate = signupDate;
    }
}
