package com.auth.ums.Controller;

import com.auth.ums.Models.User;
import com.auth.ums.RequestModel.AddUserRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.Service.UserService.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    ResponseEntity<ApiResponse<User>> adduser(@Valid @RequestBody AddUserRequest request){
        return ResponseEntity.ok(userService.adduser(request));
    }
}



