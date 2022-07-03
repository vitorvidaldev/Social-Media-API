package com.example.social.media.application.service;

import com.example.social.media.domain.entity.Post;
import com.example.social.media.domain.repository.PostRepository;
import com.example.social.media.domain.vo.post.CreatePostVo;
import com.example.social.media.domain.vo.post.PostVo;
import com.example.social.media.domain.vo.user.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public record PostService(PostRepository postRepository, UserService userService, FollowerService followerService) {

    public List<PostVo> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostVo> postVoList = new ArrayList<>();
        for (Post post : posts) {
            postVoList.add(new PostVo(
                    post.getPostId(),
                    post.getPostContent(),
                    post.getWriterName(),
                    post.getCreationDate(),
                    post.isRepost(),
                    post.getQuote(),
                    post.getWriterId()));
        }
        return postVoList;
    }

    public PostVo createNewPost(UUID userId, CreatePostVo createPostVo) {
        UserVo user = userService.findUserById(userId);

        List<PostVo> postVoList = getUserPostsFromLast24Hours(user);
        if (postVoList.size() < 5) {
            Post post = postRepository.save(new Post(
                    createPostVo.getContent(),
                    userId,
                    user.username(),
                    createPostVo.isRepost(),
                    createPostVo.getQuote()));
            return new PostVo(
                    post.getPostId(),
                    post.getPostContent(),
                    user.username(),
                    post.getCreationDate(),
                    post.isRepost(),
                    post.getQuote(),
                    post.getWriterId());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Daily Posts Limit Reached");
    }

    private List<PostVo> getUserPostsFromLast24Hours(UserVo user) {
        List<PostVo> userPosts = getUserPosts(user.userId());
        List<PostVo> postVoList = new ArrayList<>();
        for (PostVo postVo : userPosts) {
            if (postVo.getCreationDate().isAfter(LocalDateTime.now().minusDays(1))) {
                postVoList.add(postVo);
            }
        }
        return postVoList;
    }

    public List<PostVo> getUserPosts(UUID userId) {
        Optional<List<Post>> optionalPostList = postRepository.findByWriterId(userId);

        if (optionalPostList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Posts not found");
        }

        List<PostVo> postVoList = new ArrayList<>();
        for (Post post : optionalPostList.get()) {
            postVoList.add(new PostVo(
                    post.getPostId(),
                    post.getPostContent(),
                    post.getWriterName(),
                    post.getCreationDate(),
                    post.isRepost(),
                    post.getQuote(),
                    post.getWriterId()));
        }
        return postVoList;
    }

    public PostVo repost(UUID userId, UUID postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with given id was not found");
        }
        return createNewPost(userId, new CreatePostVo(optionalPost.get().getPostContent(), true, null));
    }

    public PostVo quote(UUID userId, UUID postId, CreatePostVo quoteVo) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with given id was not found");
        }
        return createNewPost(userId, new CreatePostVo(optionalPost.get().getPostContent(), false, quoteVo.getQuote()));
    }
}
