package com.example.social.media.application.controller;

import com.example.social.media.application.service.FollowService;
import com.example.social.media.domain.vo.FollowVo;
import com.example.social.media.domain.vo.FollowerVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/rest/v1")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<FollowVo> followUser(
            @PathVariable(value = "userId") UUID userId,
            @Valid @RequestBody FollowerVo followerVo) {
        FollowVo followVo = followService.followUser(userId, followerVo);
        return ResponseEntity.status(HttpStatus.CREATED).body(followVo);
    }

    @PostMapping("/unfollow/{userId}")
    public ResponseEntity<FollowVo> unfollowUser(
            @PathVariable(value = "userId") UUID userId,
            @Valid @RequestBody FollowerVo followerVo) {
        FollowVo followVo = followService.unfollowUser(userId, followerVo);
        return ResponseEntity.status(HttpStatus.CREATED).body(followVo);
    }
}
