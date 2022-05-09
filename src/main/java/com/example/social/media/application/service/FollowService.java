package com.example.social.media.application.service;

import com.example.social.media.domain.entity.Follow;
import com.example.social.media.domain.repository.FollowRepository;
import com.example.social.media.domain.vo.FollowVo;
import com.example.social.media.domain.vo.FollowerVo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class FollowService {

    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public FollowVo followUser(UUID userId, FollowerVo followerVo) {
        if (userId.equals(followerVo.getFollowerId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't follow yourself");
        }
        addFollow(userId, followerVo);
        addFollowerData(userId, followerVo);
        return new FollowVo(userId, followerVo.getFollowerId());
    }

    private void addFollowerData(UUID userId, FollowerVo followerVo) {
        Follow followerData = findFollowByUserId(followerVo.getFollowerId());
        followerData.addFollower(userId);
        followRepository.save(followerData);
    }

    private void addFollow(UUID userId, FollowerVo followerVo) {
        Follow userFollowData = findFollowByUserId(userId);
        userFollowData.addFollow(followerVo.getFollowerId());
        followRepository.save(userFollowData);
    }

    public FollowVo unfollowUser(UUID userId, FollowerVo followerVo) {
        if (userId.equals(followerVo.getFollowerId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't unfollow yourself");
        }

        removeFollow(userId, followerVo);

        removeFollowerData(userId, followerVo);
        return new FollowVo(userId, followerVo.getFollowerId());
    }

    private void removeFollowerData(UUID userId, FollowerVo followerVo) {
        Follow followerData = findFollowByUserId(followerVo.getFollowerId());
        followerData.removeFollower(userId);
        followRepository.save(followerData);
    }

    private void removeFollow(UUID userId, FollowerVo followerVo) {
        Follow userFollowData = findFollowByUserId(userId);
        userFollowData.removeFollow(followerVo.getFollowerId());
        followRepository.save(userFollowData);
    }

    public Follow findFollowByUserId(UUID userId) {
        Optional<Follow> optionalFollow = followRepository.findById(userId);
        return (optionalFollow.isEmpty()) ? new Follow(userId) : optionalFollow.get();
    }
}
