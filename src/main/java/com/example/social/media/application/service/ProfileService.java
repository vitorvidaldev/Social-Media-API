package com.example.social.media.application.service;

import com.example.social.media.domain.vo.profile.ProfileVo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record ProfileService(UserService userService, FollowerService followerService, PostService postService) {

    public ProfileVo getProfileData(UUID loggedUserId, UUID otherUserId) {
//        UserVo userVo = userService.findUserById(otherUserId);
//        Follower followerData = followerService.findFollowByUserId(otherUserId);
//        List<PostVo> userPosts = postService.getUserPosts(otherUserId);
//
//        return new ProfileVo(
//                userVo.username(),
//                userVo.signupDate(),
//                followerData.getUserFollowers().size(),
//                followerData.getUserFollows().size(),
//                userPosts.size(),
//                followerData.getUserFollowers().contains(loggedUserId));
        return null;
    }
}
