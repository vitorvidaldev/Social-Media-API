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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FollowerServiceTest {

    private final UUID userIdMock = UUID.randomUUID();
    private final SetFollowerVo setFollowerVoMock = new SetFollowerVo(UUID.randomUUID());
    @InjectMocks
    private FollowerService followerService;
    @Mock
    private FollowerRepository followerRepository;

    @Test
    void shouldFollowUserCorrectly() {
        Follower followerMock = mock(Follower.class);
        Optional<Follower> optionalFollowMock = Optional.of(followerMock);

        when(followerRepository.findById(userIdMock)).thenReturn(optionalFollowMock);
        when(followerRepository.save(followerMock)).thenReturn(followerMock);

        FollowerVo followerVo = followerService.followUser(userIdMock, setFollowerVoMock);

        assertNotNull(followerVo);
        assertEquals(userIdMock, followerVo.userId());
        assertEquals(setFollowerVoMock.followerId(), followerVo.followerId());
    }

    @Test
    void shouldThrowBadRequestExceptionFollowingItself() {
        Follower followerMock = mock(Follower.class);
        Optional<Follower> optionalFollowMock = Optional.of(followerMock);

        SetFollowerVo setFollowerVoMock = new SetFollowerVo(userIdMock);


        when(followerRepository.findById(userIdMock)).thenReturn(optionalFollowMock);
        when(followerRepository.save(followerMock)).thenReturn(followerMock);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> followerService.followUser(userIdMock, setFollowerVoMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("You can't follow yourself", exception.getReason());
    }

    @Test
    void shouldThrowBadRequestExceptionUnfollowingItself() {
        Follower followerMock = mock(Follower.class);
        Optional<Follower> optionalFollowMock = Optional.of(followerMock);

        SetFollowerVo setFollowerVoMock = new SetFollowerVo(userIdMock);


        when(followerRepository.findById(userIdMock)).thenReturn(optionalFollowMock);
        when(followerRepository.save(followerMock)).thenReturn(followerMock);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> followerService.unfollowUser(userIdMock, setFollowerVoMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("You can't unfollow yourself", exception.getReason());
    }
}