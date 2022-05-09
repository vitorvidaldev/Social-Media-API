package com.example.social.media.domain.repository;

import com.example.social.media.domain.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends MongoRepository<Post, UUID> {

    Optional<List<Post>> findByWriterId(UUID writerId);
}
