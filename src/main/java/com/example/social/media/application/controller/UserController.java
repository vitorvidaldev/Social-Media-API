package com.example.social.media.application.controller;

import com.example.social.media.application.service.UserService;
import com.example.social.media.domain.vo.user.CreateUserVo;
import com.example.social.media.domain.vo.user.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserVo> getUserData(@PathVariable(value = "userId") String userId) {
        UserVo userVo = userService.getUserData(userId);
        return ResponseEntity.ok().body(userVo);
    }

    @PostMapping
    public ResponseEntity<UserVo> createUser(@RequestBody CreateUserVo createUserVo) {
        UserVo userVo = userService.createUser(createUserVo);
        return ResponseEntity.status(HttpStatus.CREATED).body(userVo);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "userId") String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
