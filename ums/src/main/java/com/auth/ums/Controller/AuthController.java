package com.auth.ums.Controller;

import com.auth.ums.Models.User;
import com.auth.ums.RequestModel.Auth.AddUserRequest;
import com.auth.ums.RequestModel.Auth.ForgetPasswordRequest;
import com.auth.ums.RequestModel.Auth.LoginRequest;
import com.auth.ums.RequestModel.Auth.RefreshTokenRequest;
import com.auth.ums.RequestModel.PasswordForgetRequestModel.PasswordChangeByToken;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.Auth.ForgetPasswordResponse;
import com.auth.ums.ResponseModel.Auth.LoginResponse;
import com.auth.ums.ResponseModel.ChangePasswordByToken.ChangePasswordByTokenResponse;
import com.auth.ums.Service.AuthService.AuthService;
import com.auth.ums.Service.UserService.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @PostMapping("signup")
    ResponseEntity<ApiResponse<User>> signup(@Valid @RequestBody AddUserRequest request){
        return ResponseEntity.ok(userService.adduser(request));
    }
    @PostMapping("login")
    ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("refresh-token")
    ResponseEntity<ApiResponse<LoginResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }

    @PostMapping("forget-password")
    ResponseEntity<ApiResponse<ForgetPasswordResponse>> forgetPassword(@Valid @RequestBody ForgetPasswordRequest request) {
        return ResponseEntity.ok(authService.forgetPassword(request));

    }
    @PostMapping("change-password")
    ResponseEntity<ApiResponse<ChangePasswordByTokenResponse>> changePassword(@Valid @RequestBody PasswordChangeByToken request) {
        return ResponseEntity.ok(authService.passwordChangeByToken(request));

    }
}
