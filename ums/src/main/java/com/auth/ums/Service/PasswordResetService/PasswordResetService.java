package com.auth.ums.Service.PasswordResetService;

import com.auth.ums.RequestModel.PasswordForgetRequestModel.AddPasswordResetRequest;
import com.auth.ums.RequestModel.PasswordForgetRequestModel.PasswordChangeByToken;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.PasswordReset.PasswordResetResponse;
import com.auth.ums.configs.ApiResponseCodes;

public interface PasswordResetService {

    ApiResponse<PasswordResetResponse>forgetPassword(AddPasswordResetRequest request);
    ApiResponse<PasswordResetResponse>changePasswordByToken(PasswordChangeByToken request);

}
