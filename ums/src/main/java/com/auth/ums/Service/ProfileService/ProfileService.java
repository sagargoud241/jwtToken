package com.auth.ums.Service.ProfileService;

import com.auth.ums.RequestModel.ProfileModel.ChangePasswordRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.Profile.ProfileResponse;

public interface ProfileService {
    ApiResponse<ProfileResponse> resetPassword(ChangePasswordRequest request);
}
