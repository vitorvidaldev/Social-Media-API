package com.example.social.media.application.service;

import com.example.social.media.domain.entity.Follow;
import com.example.social.media.domain.repository.FollowRepository;
import com.example.social.media.domain.vo.follow.FollowVo;
import com.example.social.media.domain.vo.follow.FollowerVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FollowServiceTest {

    private final UUID userIdMock = UUID.randomUUID();
    private final FollowerVo followerVoMock = new FollowerVo(UUID.randomUUID());
    @InjectMocks
    private FollowService followService;
    @Mock
    private FollowRepository followRepository;

    @Test
    void shouldFollowUserCorrectly() {
        Follow followMock = mock(Follow.class);
        Optional<Follow> optionalFollowMock = Optional.of(followMock);

        when(followRepository.findById(userIdMock)).thenReturn(optionalFollowMock);
        when(followRepository.save(followMock)).thenReturn(followMock);

        FollowVo followVo = followService.followUser(userIdMock, followerVoMock);

        assertNotNull(followVo);
        assertEquals(userIdMock, followVo.getUserId());
        assertEquals(followerVoMock.getFollowerId(), followVo.getFollowerId());
    }

    @Test
    void shouldThrowBadRequestExceptionFollowingItself() {
        Follow followMock = mock(Follow.class);
        Optional<Follow> optionalFollowMock = Optional.of(followMock);

        FollowerVo followerVoMock = new FollowerVo(userIdMock);


        when(followRepository.findById(userIdMock)).thenReturn(optionalFollowMock);
        when(followRepository.save(followMock)).thenReturn(followMock);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> followService.followUser(userIdMock, followerVoMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("You can't follow yourself", exception.getReason());
    }

    @Test
    void shouldUnfollowUserCorrectly() {
        Follow followMock = mock(Follow.class);
        Optional<Follow> optionalFollowMock = Optional.of(followMock);

        when(followRepository.findById(userIdMock)).thenReturn(optionalFollowMock);
        when(followRepository.save(followMock)).thenReturn(followMock);

        FollowVo followVo = followService.unfollowUser(userIdMock, followerVoMock);

        assertNotNull(followVo);
        assertEquals(userIdMock, followVo.getUserId());
        assertEquals(followerVoMock.getFollowerId(), followVo.getFollowerId());
    }

    @Test
    void shouldThrowBadRequestExceptionUnfollowingItself() {
        Follow followMock = mock(Follow.class);
        Optional<Follow> optionalFollowMock = Optional.of(followMock);

        FollowerVo followerVoMock = new FollowerVo(userIdMock);


        when(followRepository.findById(userIdMock)).thenReturn(optionalFollowMock);
        when(followRepository.save(followMock)).thenReturn(followMock);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> followService.unfollowUser(userIdMock, followerVoMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("You can't unfollow yourself", exception.getReason());
    }

    @Test
    void shouldFindFollowByUserIdCorrectly() {
        Follow followMock = mock(Follow.class);
        Optional<Follow> optionalFollowMock = Optional.of(followMock);

        when(followRepository.findById(userIdMock)).thenReturn(optionalFollowMock);
        Follow follow = followService.findFollowByUserId(userIdMock);

        assertNotNull(follow);
    }
}