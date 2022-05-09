package com.example.social.media.domain.repository;

import com.example.social.media.domain.entity.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface FollowRepository extends MongoRepository<Follow, UUID> {
}
