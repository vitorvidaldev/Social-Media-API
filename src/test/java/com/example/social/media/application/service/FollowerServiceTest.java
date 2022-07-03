package com.example.social.media.application.service;

import com.example.social.media.domain.entity.Follower;
import com.example.social.media.domain.repository.FollowerRepository;
import com.example.social.media.domain.vo.follower.FollowerVo;
import com.example.social.media.domain.vo.follower.SetFollowerVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FollowerServiceTest {
    @InjectMocks
    private FollowerService followerService;
    @Mock
    private FollowerRepository followerRepository;

    @Test
    public void shouldFollowUserCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        UUID followerIdMock = UUID.randomUUID();
        SetFollowerVo setFollowerVoMock = mock(SetFollowerVo.class);
        Follower followerMock = mock(Follower.class);

        when(setFollowerVoMock.followerId()).thenReturn(followerIdMock);

        when(followerMock.getFollowerId()).thenReturn(followerIdMock);
        when(followerMock.getUserId()).thenReturn(userIdMock);

        when(followerRepository.save(any(Follower.class))).thenReturn(followerMock);

        FollowerVo followerVo = followerService.followUser(userIdMock, setFollowerVoMock);

        assertNotNull(followerVo);
        assertEquals(userIdMock, followerVo.userId());
        assertEquals(followerIdMock, followerVo.followerId());

        verify(followerRepository).save(any(Follower.class));
        verify(setFollowerVoMock, times(2)).followerId();
        verify(followerMock).getFollowerId();
        verify(followerMock).getUserId();
    }

    @Test
    public void shouldThrowBadRequestExceptionFollowingYourself() {
        UUID userIdMock = UUID.randomUUID();
        SetFollowerVo setFollowerVo = mock(SetFollowerVo.class);

        when(setFollowerVo.followerId()).thenReturn(userIdMock);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> followerService.followUser(userIdMock, setFollowerVo)
        );

        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("You can't follow yourself", exception.getReason());

        verify(setFollowerVo).followerId();
    }

    @Test
    public void shouldThrowBadRequestFollowingUserAgain() {
        UUID userIdMock = UUID.randomUUID();
        UUID followerIdMock = UUID.randomUUID();
        SetFollowerVo setFollowerVoMock = mock(SetFollowerVo.class);

        when(setFollowerVoMock.followerId()).thenReturn(followerIdMock);

        when(followerRepository.save(any(Follower.class))).thenThrow(RuntimeException.class);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> followerService.followUser(userIdMock, setFollowerVoMock)
        );

        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("You already follow this user", exception.getReason());

        verify(setFollowerVoMock, times(2)).followerId();
        verify(followerRepository).save(any(Follower.class));
    }

    @Test
    public void shouldUnfollowUserCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        UUID followerIdMock = UUID.randomUUID();
        SetFollowerVo setFollowerVoMock = mock(SetFollowerVo.class);

        when(setFollowerVoMock.followerId()).thenReturn(followerIdMock);

        doNothing().when(followerRepository).deleteByUserIdAndFollowerId(userIdMock, followerIdMock);

        assertDoesNotThrow(() -> followerService.unfollowUser(userIdMock, setFollowerVoMock));

        verify(setFollowerVoMock, times(2)).followerId();
        verify(followerRepository).deleteByUserIdAndFollowerId(userIdMock, followerIdMock);
    }

    @Test
    public void shouldThrowBadRequestExceptionUnfollowingYourself() {
        UUID userIdMock = UUID.randomUUID();
        SetFollowerVo setFollowerVoMock = mock(SetFollowerVo.class);

        when(setFollowerVoMock.followerId()).thenReturn(userIdMock);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> followerService.unfollowUser(userIdMock, setFollowerVoMock)
        );

        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("You can't unfollow yourself", exception.getReason());

        verify(setFollowerVoMock).followerId();
    }

    @Test
    public void shouldGetFollowerListCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        UUID followerIdMock = UUID.randomUUID();
        Follower followerMock = mock(Follower.class);

        when(followerMock.getFollowerId()).thenReturn(followerIdMock);

        when(followerRepository.findByUserId(userIdMock)).thenReturn(List.of(followerMock));

        List<String> followerList = followerService.getFollowerList(userIdMock);

        assertNotNull(followerList);
        assertEquals(followerIdMock.toString(), followerList.get(0));

        verify(followerRepository).findByUserId(userIdMock);
        verify(followerMock).getFollowerId();
    }
}