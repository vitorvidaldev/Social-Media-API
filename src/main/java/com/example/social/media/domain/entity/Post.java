package com.example.social.media.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document("post")
public class Post {
    @MongoId(FieldType.STRING)
    private UUID postId;
    @Size(max = 777)
    private String postContent;
    @Indexed
    private UUID writerId;
    private String writerName;
    private boolean isRepost;
    private LocalDateTime creationDate;
    private String quote;

    public Post() {
    }

    public Post(UUID postId) {
        this.postId = postId;
    }

    public Post(String postContent, UUID writerId, String writerName, boolean isRepost, String quote) {
        this.postId = UUID.randomUUID();
        this.postContent = postContent;
        this.writerId = writerId;
        this.creationDate = LocalDateTime.now();
        this.writerName = writerName;
        this.isRepost = isRepost;
        this.quote = quote;
    }
}
