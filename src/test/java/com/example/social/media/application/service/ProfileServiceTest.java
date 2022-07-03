package com.example.social.media.application.service;

import com.example.social.media.domain.vo.post.PostVo;
import com.example.social.media.domain.vo.profile.ProfileVo;
import com.example.social.media.domain.vo.user.UserVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProfileServiceTest {
    @InjectMocks
    private ProfileService profileService;
    @Mock
    private UserService userService;
    @Mock
    private FollowerService followerService;
    @Mock
    private PostService postService;

    @Test
    void shouldGetProfileDataCorrectly() {
        UUID userId = UUID.randomUUID();

        UserVo userVoMock = mock(UserVo.class);
        PostVo postVoMock = mock(PostVo.class);
        List<PostVo> postVoList = List.of(postVoMock);

        when(userService.findUserById(userId)).thenReturn(userVoMock);
        when(postService.getUserPosts(userId)).thenReturn(postVoList);

        ProfileVo profileData = profileService.getProfileData(userId);

        assertNotNull(profileData);
        assertEquals(postVoList.size(), profileData.numberOfPosts());
    }
}