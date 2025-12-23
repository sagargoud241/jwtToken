package com.auth.ums.Service.UserService;

import com.auth.ums.Models.User;
import com.auth.ums.RequestModel.Auth.AddUserRequest;
import com.auth.ums.RequestModel.Auth.LoginRequest;
import com.auth.ums.RequestModel.PasswordForgetRequestModel.PasswordChangeByToken;
import com.auth.ums.RequestModel.ProfileModel.ChangePasswordRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.user.UserResponse;
import jakarta.validation.constraints.NotBlank;

public interface UserService {
    ApiResponse<User> adduser(AddUserRequest request);

    ApiResponse<UserResponse> login(LoginRequest request);

    ApiResponse<UserResponse> findByUserId(Long id);

    ApiResponse<UserResponse> changePasswordByLoggedInUser(String userName, ChangePasswordRequest request);

    ApiResponse<UserResponse> findByUserName(@NotBlank(message = "User name is Required ") String userName);

    ApiResponse<UserResponse> passwordChangeByToken(Long userId, PasswordChangeByToken request);
}
