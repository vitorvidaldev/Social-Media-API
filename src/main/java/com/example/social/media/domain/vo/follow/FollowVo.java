package com.example.social.media.domain.vo.follow;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FollowVo {
    private UUID userId;
    private UUID followerId;

    public FollowVo() {
    }

    public FollowVo(UUID userId, UUID followerId) {
        this.userId = userId;
        this.followerId = followerId;
    }
}