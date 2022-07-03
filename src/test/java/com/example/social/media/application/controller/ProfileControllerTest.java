package com.example.social.media.application.controller;

import com.example.social.media.application.service.ProfileService;
import com.example.social.media.domain.vo.profile.ProfileVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProfileControllerTest {

    private final UUID loggedUserId = UUID.randomUUID();
    private final UUID otherUserId = UUID.randomUUID();
    @InjectMocks
    private ProfileController profileController;
    @Mock
    private ProfileService profileService;

    @Test
    void shouldGetProfileDataCorrectly() {
        ProfileVo profileVoMock = mock(ProfileVo.class);

        // when
        when(profileService.getProfileData(otherUserId)).thenReturn(profileVoMock);
        // then
        ResponseEntity<ProfileVo> response = profileController.getProfileData(otherUserId);
        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}