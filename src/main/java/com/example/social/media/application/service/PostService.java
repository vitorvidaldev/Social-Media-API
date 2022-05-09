package com.example.social.media.application.service;

import com.example.social.media.domain.entity.Follow;
import com.example.social.media.domain.entity.Post;
import com.example.social.media.domain.repository.PostRepository;
import com.example.social.media.domain.vo.CreatePostVo;
import com.example.social.media.domain.vo.PostVo;
import com.example.social.media.domain.vo.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final FollowService followService;

    public PostService(PostRepository postRepository, UserService userService, FollowService followService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.followService = followService;
    }

    public List<PostVo> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostVo> postVoList = new ArrayList<>();
        for (Post post : posts) {
            postVoList.add(new PostVo(
                    post.getId(),
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
                    user.getUsername(),
                    createPostVo.isRepost(),
                    createPostVo.getQuote()));
            return new PostVo(
                    post.getId(),
                    post.getPostContent(),
                    user.getUsername(),
                    post.getCreationDate(),
                    post.isRepost(),
                    post.getQuote(),
                    post.getWriterId());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Daily Posts Limit Reached");
    }

    private List<PostVo> getUserPostsFromLast24Hours(UserVo user) {
        List<PostVo> userPosts = getUserPosts(user.getUserId());
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
                    post.getId(),
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

    public List<PostVo> getFollowerPosts(UUID userId) {
        Follow userFollowData = followService.findFollowByUserId(userId);
        List<PostVo> allPosts = getAllPosts();
        return allPosts.stream().filter(post -> userFollowData.getUserFollows().contains(post.getUserId())).collect(Collectors.toList());
    }
}
