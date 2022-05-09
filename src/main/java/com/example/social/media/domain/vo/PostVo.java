package com.example.social.media.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PostVo {
    private UUID id;
    private String postContent;
    private String username;
    private boolean isRepost;
    private String quote;
    private LocalDateTime creationDate;
    private UUID userId;

    public PostVo() {
    }

    public PostVo(UUID id,
                  String postContent,
                  String username,
                  LocalDateTime creationDate,
                  boolean isRepost,
                  String quote,
                  UUID userId) {
        this.postContent = postContent;
        this.username = username;
        this.creationDate = creationDate;
        this.id = id;
        this.isRepost = isRepost;
        this.quote = quote;
        this.userId = userId;
    }
}
