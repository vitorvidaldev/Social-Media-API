package com.example.social.media.application.service;

import com.example.social.media.domain.entity.Follower;
import com.example.social.media.domain.repository.FollowerRepository;
import com.example.social.media.domain.vo.follower.FollowerVo;
import com.example.social.media.domain.vo.follower.SetFollowerVo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public record FollowerService(FollowerRepository followerRepository) {

    public FollowerVo followUser(UUID userId, SetFollowerVo setFollowerVo) {
        if (userId.equals(setFollowerVo.followerId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't follow yourself");
        }

        try {
            Follower follower = followerRepository.save(new Follower(userId, setFollowerVo.followerId()));
            return new FollowerVo(follower.getUserId(), follower.getFollowerId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already follow this user");
        }
    }

    public void unfollowUser(UUID userId, SetFollowerVo setFollowerVo) {
        if (userId.equals(setFollowerVo.followerId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't unfollow yourself");
        }
        followerRepository.deleteByUserIdAndFollowerId(userId, setFollowerVo.followerId());
    }

    public List<FollowerVo> getFollowerList(UUID userId) {
        List<Follower> followerList = followerRepository.findByUserId(userId);
        return followerList.stream().map(follower -> new FollowerVo(
                follower.getUserId(),
                follower.getFollowerId()
        )).toList();
    }
}
