package com.auth.ums.Service.AuthService;

import com.auth.ums.RequestModel.Auth.ForgetPasswordRequest;
import com.auth.ums.RequestModel.Auth.LoginRequest;
import com.auth.ums.RequestModel.PasswordForgetRequestModel.PasswordChangeByToken;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.Auth.ForgetPasswordResponse;
import com.auth.ums.ResponseModel.Auth.LoginResponse;
import com.auth.ums.ResponseModel.ChangePasswordByToken.ChangePasswordByTokenResponse;
import jakarta.validation.Valid;


public interface AuthService {
    ApiResponse<LoginResponse> login(LoginRequest request);

    ApiResponse<LoginResponse> refreshToken(String refreshToken);

    ApiResponse<ForgetPasswordResponse> forgetPassword(@Valid ForgetPasswordRequest request);

    ApiResponse<ChangePasswordByTokenResponse> passwordChangeByToken(@Valid PasswordChangeByToken request);

}
