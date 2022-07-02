package com.example.social.media.domain.vo.follower;

import java.util.UUID;

public record FollowerVo(UUID userId, UUID followerId) {
}