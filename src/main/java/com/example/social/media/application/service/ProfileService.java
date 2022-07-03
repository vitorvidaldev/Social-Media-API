package com.example.social.media.application.service;

import com.example.social.media.domain.vo.post.PostVo;
import com.example.social.media.domain.vo.profile.ProfileVo;
import com.example.social.media.domain.vo.user.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record ProfileService(UserService userService, FollowerService followerService, PostService postService) {

    public ProfileVo getProfileData(UUID userId) {
        UserVo userVo = userService.findUserById(userId);
        List<String> followerList = followerService.getFollowerList(userId);
        List<PostVo> userPosts = postService.getUserPosts(userId);

        return new ProfileVo(
                userVo.username(),
                userVo.signupDate(),
                followerList.size(),
                userPosts.size());
    }
}
