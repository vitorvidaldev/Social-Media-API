package com.example.social.media.application.controller;

import com.example.social.media.application.service.UserService;
import com.example.social.media.domain.vo.user.CreateUserVo;
import com.example.social.media.domain.vo.user.UserVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @Test
    public void shouldGetUserDataCorrectly() {
        UserVo userVoMock = mock(UserVo.class);
        String userIdMock = UUID.randomUUID().toString();

        when(userService.getUserData(userIdMock)).thenReturn(userVoMock);

        ResponseEntity<UserVo> response = userController.getUserData(userIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userVoMock, response.getBody());

        verify(userService).getUserData(userIdMock);
    }

    @Test
    public void shouldCreateUserCorrectly() {
        UserVo userVoMock = mock(UserVo.class);
        CreateUserVo createUserVoMock = mock(CreateUserVo.class);

        when(userService.createUser(createUserVoMock)).thenReturn(userVoMock);

        ResponseEntity<UserVo> response = userController.createUser(createUserVoMock);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userVoMock, response.getBody());

        verify(userService).createUser(createUserVoMock);
    }

    @Test
    public void shouldDeleteUserCorrectly() {
        String userIdMock = UUID.randomUUID().toString();

        doNothing().when(userService).deleteUser(userIdMock);

        ResponseEntity<Void> response = userController.deleteUser(userIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(userService).deleteUser(userIdMock);
    }
}