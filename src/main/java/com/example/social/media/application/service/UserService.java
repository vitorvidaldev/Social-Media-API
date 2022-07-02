package com.example.social.media.application.service;

import com.example.social.media.domain.entity.User;
import com.example.social.media.domain.repository.UserRepository;
import com.example.social.media.domain.vo.user.CreateUserVo;
import com.example.social.media.domain.vo.user.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public record UserService(UserRepository userRepository) {

    public UserVo findUserById(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return new UserVo(
                optionalUser.get().getUsername(),
                optionalUser.get().getUserId(),
                optionalUser.get().getSignupDate());
    }

    public UserVo getUserData(String userId) {
        return null;
    }

    public UserVo createUser(CreateUserVo createUserVo) {
        return null;
    }

    public void deleteUser(String userId) {

    }
}
