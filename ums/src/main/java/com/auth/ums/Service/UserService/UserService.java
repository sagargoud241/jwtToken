package com.auth.ums.Service.UserService;

import com.auth.ums.Models.User;
import com.auth.ums.RequestModel.AddUserRequest;
import com.auth.ums.RequestModel.LoginRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.user.UserResponse;

public interface UserService {
    ApiResponse<User> adduser(AddUserRequest request);

    ApiResponse<UserResponse> login(LoginRequest request);

    ApiResponse<UserResponse> findByUserId(Long id);
}
