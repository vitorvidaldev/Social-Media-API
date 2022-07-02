package com.example.social.media.domain.repository;

import com.example.social.media.domain.entity.Follower;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface FollowerRepository extends MongoRepository<Follower, UUID> {

    void deleteByUserIdAndFollowerId(UUID userId, UUID followerId);

    List<Follower> findByUserId(UUID userId);
}
