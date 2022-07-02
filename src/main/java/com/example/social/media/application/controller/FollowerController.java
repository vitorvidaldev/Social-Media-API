package com.example.social.media.application.controller;

import com.example.social.media.application.service.FollowerService;
import com.example.social.media.domain.vo.follower.FollowerVo;
import com.example.social.media.domain.vo.follower.SetFollowerVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/follower")
public class FollowerController {

    private final FollowerService followerService;

    public FollowerController(FollowerService followerService) {
        this.followerService = followerService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FollowerVo>> getFollowerList(@PathVariable(value = "userId") UUID userId) {
        List<FollowerVo> followerList = followerService.getFollowerList(userId);
        return ResponseEntity.ok().body(followerList);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<FollowerVo> followUser(
            @PathVariable(value = "userId") UUID userId,
            @Valid @RequestBody SetFollowerVo setFollowerVo) {
        FollowerVo followerVo = followerService.followUser(userId, setFollowerVo);
        return ResponseEntity.status(HttpStatus.CREATED).body(followerVo);
    }

    @PostMapping("/unfollow/{userId}")
    public ResponseEntity<Void> unfollowUser(
            @PathVariable(value = "userId") UUID userId,
            @Valid @RequestBody SetFollowerVo setFollowerVo) {
        followerService.unfollowUser(userId, setFollowerVo);
        return ResponseEntity.noContent().build();
    }
}
