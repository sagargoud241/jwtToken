package com.auth.ums.Controller;

import com.auth.ums.Models.User;
import com.auth.ums.RequestModel.AddUserRequest;
import com.auth.ums.RequestModel.LoginRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.Auth.LoginResponse;
import com.auth.ums.Service.AuthService;
import com.auth.ums.Service.UserService;
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
    ResponseEntity<ApiResponse<LoginResponse>> logibn(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
