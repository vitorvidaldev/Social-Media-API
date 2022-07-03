package com.example.social.media.application.controller;

import com.example.social.media.application.service.FollowerService;
import com.example.social.media.domain.vo.follower.FollowerVo;
import com.example.social.media.domain.vo.follower.SetFollowerVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FollowerControllerTest {
    @InjectMocks
    private FollowerController followerController;
    @Mock
    private FollowerService followerService;

    @Test
    public void shouldGetFollowerList() {
        UUID userIdMock = UUID.randomUUID();
        UUID followerIdMock = UUID.randomUUID();

        when(followerService.getFollowerList(userIdMock)).thenReturn(List.of(followerIdMock.toString()));

        ResponseEntity<List<String>> response = followerController.getFollowerList(userIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(followerIdMock.toString(), response.getBody().get(0));

        verify(followerService).getFollowerList(userIdMock);
    }

    @Test
    public void shouldFollowUserCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        SetFollowerVo setFollowerVoMock = mock(SetFollowerVo.class);
        FollowerVo followerVoMock = mock(FollowerVo.class);

        when(followerService.followUser(userIdMock, setFollowerVoMock)).thenReturn(followerVoMock);

        ResponseEntity<FollowerVo> response = followerController.followUser(userIdMock, setFollowerVoMock);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(followerVoMock, response.getBody());

        verify(followerService).followUser(userIdMock, setFollowerVoMock);
    }

    @Test
    public void shouldUnfollowUserCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        SetFollowerVo setFollowerVoMock = mock(SetFollowerVo.class);

        doNothing().when(followerService).unfollowUser(userIdMock, setFollowerVoMock);

        ResponseEntity<Void> response = followerController.unfollowUser(userIdMock, setFollowerVoMock);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(followerService).unfollowUser(userIdMock, setFollowerVoMock);
    }
}