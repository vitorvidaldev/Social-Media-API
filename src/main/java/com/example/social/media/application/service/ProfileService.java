package com.example.social.media.application.service;

import com.example.social.media.domain.entity.Follow;
import com.example.social.media.domain.vo.post.PostVo;
import com.example.social.media.domain.vo.profile.ProfileVo;
import com.example.social.media.domain.vo.user.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record ProfileService(UserService userService, FollowService followService, PostService postService) {

    public ProfileVo getProfileData(UUID loggedUserId, UUID otherUserId) {
        UserVo userVo = userService.findUserById(otherUserId);
        Follow followData = followService.findFollowByUserId(otherUserId);
        List<PostVo> userPosts = postService.getUserPosts(otherUserId);

        return new ProfileVo(
                userVo.username(),
                userVo.signupDate(),
                followData.getUserFollowers().size(),
                followData.getUserFollows().size(),
                userPosts.size(),
                followData.getUserFollowers().contains(loggedUserId));
    }
}
