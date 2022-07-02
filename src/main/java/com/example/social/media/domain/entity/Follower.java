package com.example.social.media.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document("follower")
@CompoundIndex(name = "follower_index", def = "{'userId' : 1, 'followerId': 1}", unique = true)
public class Follower {
    private UUID userId;
    private UUID followerId;

    public Follower(UUID userId, UUID followerId) {
        this.userId = userId;
        this.followerId = followerId;
    }
}
