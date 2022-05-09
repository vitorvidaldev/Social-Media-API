package com.example.social.media.application.controller;

import com.example.social.media.application.service.FollowService;
import com.example.social.media.domain.vo.FollowVo;
import com.example.social.media.domain.vo.FollowerVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FollowControllerTest {
    private final UUID loggedUserId = UUID.randomUUID();
    @InjectMocks
    private FollowController followController;
    @Mock
    private FollowService followService;

    @Test
    void shouldFollowUserCorrectly() {
        FollowerVo followerVoMock = mock(FollowerVo.class);
        FollowVo followVoMock = mock(FollowVo.class);

        // when
        when(followService.followUser(loggedUserId, followerVoMock)).thenReturn(followVoMock);
        // then
        ResponseEntity<FollowVo> response = followController.followUser(loggedUserId, followerVoMock);
        // assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldUnfollowUserCorrectly() {
        FollowerVo followerVoMock = mock(FollowerVo.class);
        FollowVo followVoMock = mock(FollowVo.class);

        // when
        when(followService.unfollowUser(loggedUserId, followerVoMock)).thenReturn(followVoMock);
        // then
        ResponseEntity<FollowVo> response = followController.unfollowUser(loggedUserId, followerVoMock);
        // assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}