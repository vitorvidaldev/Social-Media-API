package com.example.social.media.domain.vo.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserVo(String username, UUID userId, LocalDateTime signupDate) {
}
