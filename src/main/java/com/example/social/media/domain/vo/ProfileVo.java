package com.example.social.media.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileVo {
    private String username;
    private LocalDateTime creationDate;
    private int numberOfFollowers;
    private int numberOfFollows;
    private int numberOfPosts;
    private boolean isFollowing;

    public ProfileVo() {
    }

    public ProfileVo(String username,
                     LocalDateTime creationDate,
                     int numberOfFollowers,
                     int numberOfFollows,
                     int numberOfPosts,
                     boolean isFollowing) {
        this.username = username;
        this.creationDate = creationDate;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollows = numberOfFollows;
        this.numberOfPosts = numberOfPosts;
        this.isFollowing = isFollowing;
    }
}
