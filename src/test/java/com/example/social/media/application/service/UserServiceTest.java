package com.example.social.media.application.service;

import com.example.social.media.domain.vo.UserVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Test
    void shouldFindUserByIdCorrectly() {
        UserVo userVo = userService.findUserById(
                UUID.fromString("05c7b8c7-e7c5-42a1-91e0-7afbc28fb408"));

        assertNotNull(userVo);
        assertEquals("John Doe", userVo.getUsername());
    }

    @Test
    void shouldThrowNotFoundExceptionFindingUser() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.findUserById(UUID.randomUUID()));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User not found", exception.getReason());
    }
}