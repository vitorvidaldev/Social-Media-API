package com.example.social.media.application.service;

import com.example.social.media.domain.entity.User;
import com.example.social.media.domain.repository.UserRepository;
import com.example.social.media.domain.vo.user.CreateUserVo;
import com.example.social.media.domain.vo.user.UserVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldFindUserByIdCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        String usernameMock = "username";
        LocalDateTime signupDateMock = LocalDateTime.now();
        User user = mock(User.class);

        when(user.getUserId()).thenReturn(userIdMock);
        when(user.getUsername()).thenReturn(usernameMock);
        when(user.getSignupDate()).thenReturn(signupDateMock);

        when(userRepository.findById(userIdMock)).thenReturn(Optional.of(user));

        UserVo userVo = userService.findUserById(userIdMock);

        assertNotNull(userVo);
        assertEquals(userIdMock, userVo.userId());
        assertEquals(usernameMock, userVo.username());
        assertEquals(signupDateMock, userVo.signupDate());

        verify(userRepository).findById(userIdMock);
        verify(user).getUserId();
        verify(user).getUsername();
        verify(user).getSignupDate();
    }

    @Test
    public void shouldThrowNotFoundExceptionGettingUserData() {
        UUID userIdMock = UUID.randomUUID();

        when(userRepository.findById(userIdMock)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.findUserById(userIdMock)
        );

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User not found", exception.getReason());

        verify(userRepository).findById(userIdMock);
    }

    @Test
    public void shouldCreateUserCorrectly() {
        CreateUserVo createUserVoMock = mock(CreateUserVo.class);
        User userMock = mock(User.class);
        String usernameMock = "username";
        UUID userIdMock = UUID.randomUUID();
        LocalDateTime signupDateMock = LocalDateTime.now();

        when(userMock.getUserId()).thenReturn(userIdMock);
        when(userMock.getUsername()).thenReturn(usernameMock);
        when(userMock.getSignupDate()).thenReturn(signupDateMock);

        when(createUserVoMock.username()).thenReturn(usernameMock);

        when(userRepository.save(any(User.class))).thenReturn(userMock);

        UserVo userVo = userService.createUser(createUserVoMock);

        assertNotNull(userVo);
        assertEquals(userIdMock, userVo.userId());
        assertEquals(usernameMock, userVo.username());
        assertEquals(signupDateMock, userVo.signupDate());

        verify(userRepository).save(any(User.class));
        verify(userMock).getUserId();
        verify(userMock).getUsername();
        verify(userMock).getSignupDate();
        verify(createUserVoMock).username();
    }

    @Test
    public void shouldDeleteUser() {
        UUID userIdMock = UUID.randomUUID();

        doNothing().when(userRepository).deleteById(userIdMock);

        assertDoesNotThrow(() -> userService.deleteUser(userIdMock));

        verify(userRepository).deleteById(userIdMock);
    }

}