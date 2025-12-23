package com.auth.ums.Service.ProfileService;

import com.auth.ums.JwtSecurity.JwtUtil;
import com.auth.ums.RequestModel.ProfileModel.ChangePasswordRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.Profile.ProfileResponse;
import com.auth.ums.Service.UserService.UserService;
import com.auth.ums.configs.ApiResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ApiResponse<ProfileResponse> resetPassword(ChangePasswordRequest request) {
        ProfileResponse response = new ProfileResponse();
        // checking the NewPassword and conform password is same
        if (!request.getNewPassword().equals(request.getConformPassword())) {
            return ApiResponse.failure("Invalid request");
        }
        String userName = jwtUtil.getCurrentUsername();
        if (userName == null) {
            return ApiResponse.failure("Invalid request");
        } else {
            var changePassword = userService.changePasswordByLoggedInUser(userName, request);
            if (changePassword != null && changePassword.getCode().equals(ApiResponseCodes.SUCCESS)) {
                response.setDto(changePassword.getData().getUser());
                return ApiResponse.success(response, "Password updated successfully");
            } else {
                return ApiResponse.failure("Password changed failed");
            }
        }
    }
}

