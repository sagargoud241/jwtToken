package com.auth.ums.Service.ProfileService;
import com.auth.ums.JwtSecurity.JwtUtil;
import com.auth.ums.Mapper.ProfileMapper;
import com.auth.ums.Repository.UserRepository;
import com.auth.ums.RequestModel.Auth.UpdateUserRequest;
import com.auth.ums.RequestModel.ProfileModel.ChangePasswordRequest;
import com.auth.ums.RequestModel.ProfileModel.UpdateProfileRequest;
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
    @Autowired
    UserRepository userRepository;

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
                response.setMyProfile(changePassword.getData().getUser());
                return ApiResponse.success(response, "Password updated successfully");
            } else {
                return ApiResponse.failure("Password changed failed");
            }
        }
    }

    @Override
    public ApiResponse<ProfileResponse> getMyProfile() {
        ProfileResponse response = new ProfileResponse();

        var userName=jwtUtil.getCurrentUsername();
        if (userName == null) {
            return ApiResponse.failure("Invalid request");
        }
        var myProfile=userService.findByUserName(userName);

        if(myProfile!=null && myProfile.getCode().equals(ApiResponseCodes.SUCCESS)){
            response.setMyProfile(myProfile.getData().getUser());
            return ApiResponse.success(response, myProfile.getMessage());
        }else{
            return ApiResponse.failure(myProfile.getMessage());
        }
    }

    @Override
    public ApiResponse<ProfileResponse> updateProfile(UpdateProfileRequest request) {
        ProfileResponse response = new ProfileResponse();

        var userName = jwtUtil.getCurrentUsername();
        if (userName == null) {
            return ApiResponse.failure("Invalid request");
        }
        var myProfile = userService.findByUserName(userName);
        if (myProfile != null && myProfile.getCode().equals(ApiResponseCodes.SUCCESS)) {

            //Admin le update garako updateuser request tanako..!
            UpdateUserRequest userRequest = new UpdateUserRequest();
            userRequest = ProfileMapper.updateProfile(userRequest, request);
            userRequest.setId(myProfile.getData().getUser().getId());

            var updateProfile = userService.updateUser(userRequest);
            if (updateProfile != null && updateProfile.getCode().equals(ApiResponseCodes.SUCCESS)) {
                response.setMyProfile(updateProfile.getData().getUser());
                return ApiResponse.success(response, updateProfile.getMessage());
            } else {
                return ApiResponse.failure(updateProfile.getMessage());
            }
        } else {
            return ApiResponse.failure(myProfile.getMessage());
        }
    }
}