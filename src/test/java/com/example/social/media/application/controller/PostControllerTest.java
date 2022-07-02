package com.example.social.media.application.controller;

import com.example.social.media.application.service.PostService;
import com.example.social.media.domain.vo.post.CreatePostVo;
import com.example.social.media.domain.vo.post.PostVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PostControllerTest {

    private final UUID loggedUserId = UUID.randomUUID();
    private final UUID postId = UUID.randomUUID();
    @InjectMocks
    private PostController postController;
    @Mock
    private PostService postService;

    @Test
    void shouldGetAllPostsCorrectly() {
        PostVo postVoMock = mock(PostVo.class);
        List<PostVo> postVoListMock = List.of(postVoMock);

        when(postService.getAllPosts()).thenReturn(postVoListMock);
        ResponseEntity<List<PostVo>> response = postController.getAllPosts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldGetUserPostsCorrectly() {
        PostVo postVoMock = mock(PostVo.class);
        List<PostVo> postVoListMock = List.of(postVoMock);

        when(postService.getUserPosts(loggedUserId)).thenReturn(postVoListMock);
        ResponseEntity<List<PostVo>> response = postController.getUserPosts(loggedUserId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldGetFollowerPostsCorrectly() {
        PostVo postVoMock = mock(PostVo.class);
        List<PostVo> postVoListMock = List.of(postVoMock);

        when(postService.getFollowerPosts(loggedUserId)).thenReturn(postVoListMock);
        ResponseEntity<List<PostVo>> response = postController.getFollowerPosts(loggedUserId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldCreateNewPostCorrectly() {
        CreatePostVo createPostVoMock = mock(CreatePostVo.class);
        PostVo postVoMock = mock(PostVo.class);

        when(postService.createNewPost(loggedUserId, createPostVoMock)).thenReturn(postVoMock);
        ResponseEntity<PostVo> response = postController.createNewPost(loggedUserId, createPostVoMock);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldRepostCorrectly() {
        PostVo postVoMock = mock(PostVo.class);

        when(postService.repost(loggedUserId, postId)).thenReturn(postVoMock);
        ResponseEntity<PostVo> response = postController.repost(loggedUserId, postId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldQuoteCorrectly() {
        CreatePostVo createPostVoMock = mock(CreatePostVo.class);
        PostVo postVoMock = mock(PostVo.class);

        when(postService.quote(loggedUserId, postId, createPostVoMock)).thenReturn(postVoMock);
        ResponseEntity<PostVo> response = postController.quote(loggedUserId, postId, createPostVoMock);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}