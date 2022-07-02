package com.example.social.media.domain.vo.follow;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FollowerVo {
    private UUID followerId;

    public FollowerVo() {
    }

    public FollowerVo(UUID followerId) {
        this.followerId = followerId;
    }
}
