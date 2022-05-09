package com.example.social.media.application.service;

import com.example.social.media.domain.entity.Follow;
import com.example.social.media.domain.vo.PostVo;
import com.example.social.media.domain.vo.ProfileVo;
import com.example.social.media.domain.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfileService {
    private final UserService userService;
    private final FollowService followService;
    private final PostService postService;

    public ProfileService(UserService userService, FollowService followService, PostService postService) {
        this.userService = userService;
        this.followService = followService;
        this.postService = postService;
    }

    public ProfileVo getProfileData(UUID loggedUserId, UUID otherUserId) {
        UserVo userVo = userService.findUserById(otherUserId);
        Follow followData = followService.findFollowByUserId(otherUserId);
        List<PostVo> userPosts = postService.getUserPosts(otherUserId);

        return new ProfileVo(userVo.getUsername(),
                userVo.getSignupDate(),
                followData.getUserFollowers().size(),
                followData.getUserFollows().size(),
                userPosts.size(),
                followData.getUserFollowers().contains(loggedUserId));
    }
}
