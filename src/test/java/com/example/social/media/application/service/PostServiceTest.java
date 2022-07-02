package com.example.social.media.application.service;

import com.example.social.media.domain.entity.Follow;
import com.example.social.media.domain.entity.Post;
import com.example.social.media.domain.repository.PostRepository;
import com.example.social.media.domain.vo.post.CreatePostVo;
import com.example.social.media.domain.vo.post.PostVo;
import com.example.social.media.domain.vo.user.UserVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PostServiceTest {
    private final UUID userIdMock = UUID.randomUUID();
    private final UserVo userVoMock = new UserVo(
            "Test user",
            userIdMock,
            LocalDateTime.now());
    private final Post postMock = new Post(
            "Test content",
            userIdMock,
            "Test user",
            false,
            null
    );
    private final Optional<List<Post>> optionalPostList = Optional.of(List.of(postMock));
    UUID postIdMock = UUID.randomUUID();
    private final PostVo postVoMock = new PostVo(
            postIdMock,
            "Test content",
            "Test user",
            LocalDateTime.now(),
            false,
            null,
            userIdMock
    );
    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserService userService;
    @Mock
    private FollowService followService;

    @Test
    void shouldGetAllPostsCorrectly() {
        List<Post> postList = List.of(postMock);

        when(postRepository.findAll()).thenReturn(postList);

        List<PostVo> allPosts = postService.getAllPosts();

        assertNotNull(allPosts);
        assertEquals(postList.size(), allPosts.size());
        assertEquals(postVoMock.getPostContent(), allPosts.get(0).getPostContent());
        assertEquals(postVoMock.getUserId(), allPosts.get(0).getUserId());
        assertEquals(postVoMock.getUsername(), allPosts.get(0).getUsername());
    }

    @Test
    void shouldCreateNewPostCorrectly() {
        List<Post> postList = List.of(postMock);
        Optional<List<Post>> optionalPostList = Optional.of(postList);

        CreatePostVo createPostVoMock = mock(CreatePostVo.class);

        when(userService.findUserById(userIdMock)).thenReturn(userVoMock);
        when(postRepository.findAll()).thenReturn(postList);
        when(postRepository.save(any())).thenReturn(postMock);
        when(postRepository.findByWriterId(userIdMock)).thenReturn(optionalPostList);

        PostVo newPost = postService.createNewPost(userIdMock, createPostVoMock);

        assertNotNull(newPost);
        assertEquals(postVoMock.getPostContent(), newPost.getPostContent());
        assertEquals(postVoMock.getUserId(), newPost.getUserId());
        assertEquals(postVoMock.getUsername(), newPost.getUsername());
    }

    @Test
    void shouldThrowBadRequestExceptionCreatingPost() {
        List<Post> postList = List.of(postMock, postMock, postMock, postMock, postMock, postMock);
        Optional<List<Post>> optionalPostList = Optional.of(postList);

        CreatePostVo createPostVoMock = mock(CreatePostVo.class);

        when(userService.findUserById(userIdMock)).thenReturn(userVoMock);
        when(postRepository.findAll()).thenReturn(postList);
        when(postRepository.save(any())).thenReturn(postMock);
        when(postRepository.findByWriterId(userIdMock)).thenReturn(optionalPostList);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> postService.createNewPost(userIdMock, createPostVoMock));
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Daily Posts Limit Reached", exception.getReason());
    }

    @Test
    void shouldGetUserPostsCorrectly() {
        List<Post> postList = List.of(postMock);
        Optional<List<Post>> optionalPostList = Optional.of(postList);

        when(postRepository.findByWriterId(userIdMock)).thenReturn(optionalPostList);

        List<PostVo> userPosts = postService.getUserPosts(userIdMock);

        assertNotNull(userPosts);
        assertEquals(postVoMock.getPostContent(), userPosts.get(0).getPostContent());
        assertEquals(postVoMock.getUserId(), userPosts.get(0).getUserId());
        assertEquals(postVoMock.getUsername(), userPosts.get(0).getUsername());
    }

    @Test
    void shouldThrowNotFoundExceptionGettingUserPosts() {
        when(postRepository.findByWriterId(userIdMock)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> postService.getUserPosts(userIdMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Posts not found", exception.getReason());
    }

    @Test
    void shouldRepostCorrectly() {
        Optional<Post> optionalPost = Optional.of(postMock);

        when(userService.findUserById(userIdMock)).thenReturn(userVoMock);
        when(postRepository.findById(postIdMock)).thenReturn(optionalPost);
        when(postRepository.findByWriterId(userIdMock)).thenReturn(optionalPostList);
        when(postRepository.save(any())).thenReturn(postMock);

        PostVo repost = postService.repost(userIdMock, postIdMock);

        assertNotNull(repost);
    }

    @Test
    void shouldThrowNotFoundExceptionReposting() {
        when(postRepository.findById(postIdMock)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> postService.repost(userIdMock, postIdMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Post with given id was not found", exception.getReason());
    }

    @Test
    void shouldQuoteCorrectly() {
        Optional<Post> optionalPost = Optional.of(postMock);
        CreatePostVo createPostVoMock = mock(CreatePostVo.class);

        when(userService.findUserById(userIdMock)).thenReturn(userVoMock);
        when(postRepository.findById(postIdMock)).thenReturn(optionalPost);
        when(postRepository.findByWriterId(userIdMock)).thenReturn(optionalPostList);
        when(postRepository.save(any())).thenReturn(postMock);

        PostVo repost = postService.quote(userIdMock, postIdMock, createPostVoMock);

        assertNotNull(repost);
    }

    @Test
    void shouldThrowNotFoundExceptionQuoting() {
        CreatePostVo createPostVoMock = mock(CreatePostVo.class);
        when(postRepository.findById(postIdMock)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> postService.quote(userIdMock, postIdMock, createPostVoMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Post with given id was not found", exception.getReason());

    }

    @Test
    void shouldGetFollowerPostsCorrectly() {
        Follow followMock = mock(Follow.class);

        when(followService.findFollowByUserId(userIdMock)).thenReturn(followMock);

        List<PostVo> followerPosts = postService.getFollowerPosts(userIdMock);

        assertNotNull(followerPosts);
    }
}