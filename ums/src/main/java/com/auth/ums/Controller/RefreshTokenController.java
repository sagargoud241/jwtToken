package com.auth.ums.Controller;

import com.auth.ums.RequestModel.RefreshTokenRequestModel.AddRefreshTokenRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.RefreshToken.RefreshTokenResponse;
import com.auth.ums.Service.RefreshTokenService.RefreshTokenService;
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
@RequestMapping("/api/v1/refreshToken")
public class RefreshTokenController {
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/add")
    ResponseEntity<ApiResponse<RefreshTokenResponse>> addRefreshToken(@Valid @RequestBody AddRefreshTokenRequest request) {
        return ResponseEntity.ok(refreshTokenService.addRefreshToken(request));
    }
}
