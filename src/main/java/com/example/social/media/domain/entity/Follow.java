package com.example.social.media.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Document("follow")
public class Follow {
    @MongoId
    private UUID userId;
    private Set<UUID> userFollows;
    private Set<UUID> userFollowers;

    public Follow() {
    }

    public Follow(UUID userId) {
        this.userId = userId;
        this.userFollows = new HashSet<>();
        this.userFollowers = new HashSet<>();
    }

    public Follow(UUID userId, Set<UUID> userFollows, Set<UUID> userFollowers) {
        this.userId = userId;
        this.userFollows = userFollows;
        this.userFollowers = userFollowers;
    }

    public void addFollow(UUID followerId) {
        this.userFollows.add(followerId);
    }

    public void removeFollow(UUID followId) {
        this.userFollows.removeIf(followId::equals);
    }

    public void addFollower(UUID userId) {
        this.userFollowers.add(userId);
    }

    public void removeFollower(UUID userId) {
        this.userFollowers.removeIf(userId::equals);
    }
}
