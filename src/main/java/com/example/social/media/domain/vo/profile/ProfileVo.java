package com.example.social.media.domain.vo.profile;

import java.time.LocalDateTime;

public record ProfileVo(
        String username,
        LocalDateTime creationDate,
        int numberOfFollowers,
        int numberOfPosts
) {
}
