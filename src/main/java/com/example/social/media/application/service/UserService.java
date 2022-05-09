package com.example.social.media.application.service;

import com.example.social.media.domain.entity.User;
import com.example.social.media.domain.vo.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final List<User> userList = List.of(
            new User("John Doe", "05c7b8c7-e7c5-42a1-91e0-7afbc28fb408"),
            new User("Allan Turing", "be72c153-0745-479c-adfa-3ad73d6ef884"),
            new User("Luna Johnson", "286414e3-f805-422e-8595-977bef327926"),
            new User("Violet", "61a2ff93-00f4-4db3-8d03-29015051b197"),
            new User("Hazel", "bb9a0ec7-6d58-4f7f-99c4-51536787e7d1"),
            new User("Silas", "4ce3f0ed-14b3-4e8b-9b6c-0e9396b5c618")
    );

    public UserVo findUserById(UUID id) {
        Optional<User> optionalUser = userList.stream().filter(user -> user.getUserId().equals(id)).findFirst();

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return new UserVo(optionalUser.get().getUserId(),
                optionalUser.get().getUsername(),
                optionalUser.get().getSignupDate());
    }
}
