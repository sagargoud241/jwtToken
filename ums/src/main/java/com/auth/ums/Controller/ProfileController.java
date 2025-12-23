package com.auth.ums.Controller;

import com.auth.ums.RequestModel.ProfileModel.ChangePasswordRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.Profile.ProfileResponse;
import com.auth.ums.Service.ProfileService.ProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/reset-password")
    ResponseEntity<ApiResponse<ProfileResponse>> resetPassword(@Valid @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(profileService.resetPassword(request));
    }
}
