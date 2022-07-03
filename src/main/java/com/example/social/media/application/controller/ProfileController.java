package com.example.social.media.application.controller;

import com.example.social.media.application.service.ProfileService;
import com.example.social.media.domain.vo.profile.ProfileVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileVo> getProfileData(@PathVariable(value = "userId") UUID userId) {
        ProfileVo profileVo = profileService.getProfileData(userId);
        return ResponseEntity.ok().body(profileVo);
    }
}
