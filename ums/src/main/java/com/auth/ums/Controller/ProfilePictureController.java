package com.auth.ums.Controller;

import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.ProfilePicture.ProfilePictureResponse;
import com.auth.ums.Service.ProfllePicture.ProfilePictureService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/profile-picture")
public class ProfilePictureController {

    @Autowired
    private ProfilePictureService profilePictureService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProfilePictureResponse>> upload(
            @RequestPart("profilePicture") MultipartFile profilePicture
    ) {
        if (profilePicture == null || profilePicture.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.failure("No file provided"));
        }

        return ResponseEntity.ok(
                profilePictureService.uploadProfilePicture(profilePicture)
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<ProfilePictureResponse>> deleteProfilePicture() {
        return ResponseEntity.ok(profilePictureService.deleteProfilePicture());
    }

}
