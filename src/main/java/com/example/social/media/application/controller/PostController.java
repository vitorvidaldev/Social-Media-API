package com.example.social.media.application.controller;

import com.example.social.media.application.service.PostService;
import com.example.social.media.domain.vo.post.CreatePostVo;
import com.example.social.media.domain.vo.post.PostVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostVo>> getAllPosts() {
        List<PostVo> postVoList = postService.getAllPosts();
        return ResponseEntity.ok().body(postVoList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostVo>> getUserPosts(@PathVariable(value = "userId") UUID userId) {
        List<PostVo> postVoList = postService.getUserPosts(userId);
        return ResponseEntity.ok().body(postVoList);
    }

    @GetMapping("/follow/{userId}")
    public ResponseEntity<List<PostVo>> getFollowerPosts(@PathVariable(value = "userId") UUID userId) {
        List<PostVo> postVoList = postService.getFollowerPosts(userId);
        return ResponseEntity.ok().body(postVoList);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<PostVo> createNewPost(
            @PathVariable(value = "userId") UUID userId,
            @Valid @RequestBody CreatePostVo createPostVo) {
        PostVo postVo = postService.createNewPost(userId, createPostVo);
        return ResponseEntity.status(HttpStatus.CREATED).body(postVo);
    }

    @PostMapping("/repost/{userId}/{postId}")
    public ResponseEntity<PostVo> repost(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "postId") UUID postId) {
        PostVo postVo = postService.repost(userId, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(postVo);
    }

    @PostMapping("/quote/{userId}/{postId}")
    public ResponseEntity<PostVo> quote(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "postId") UUID postId,
            @Valid @RequestBody CreatePostVo quoteVo) {
        PostVo postVo = postService.quote(userId, postId, quoteVo);
        return ResponseEntity.status(HttpStatus.CREATED).body(postVo);
    }
}
