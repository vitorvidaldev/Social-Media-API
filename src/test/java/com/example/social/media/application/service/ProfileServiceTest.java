package com.example.social.media.application.service;

import com.example.social.media.domain.entity.Follow;
import com.example.social.media.domain.vo.PostVo;
import com.example.social.media.domain.vo.ProfileVo;
import com.example.social.media.domain.vo.UserVo;
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
    private FollowService followService;
    @Mock
    private PostService postService;

    @Test
    void shouldGetProfileDataCorrectly() {
        UUID loggedUserId = UUID.randomUUID();
        UUID otherUserID = UUID.randomUUID();

        UserVo userVoMock = mock(UserVo.class);
        Follow followMock = mock(Follow.class);
        PostVo postVoMock = mock(PostVo.class);
        List<PostVo> postVoList = List.of(postVoMock);

        when(userService.findUserById(otherUserID)).thenReturn(userVoMock);
        when(followService.findFollowByUserId(otherUserID)).thenReturn(followMock);
        when(postService.getUserPosts(otherUserID)).thenReturn(postVoList);

        ProfileVo profileData = profileService.getProfileData(loggedUserId, otherUserID);

        assertNotNull(profileData);
        assertEquals(postVoList.size(), profileData.getNumberOfPosts());
    }
}