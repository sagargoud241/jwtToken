package com.auth.ums.Service;

import com.auth.ums.RequestModel.LoginRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.Auth.LoginResponse;


public interface AuthService {
    ApiResponse<LoginResponse> login(LoginRequest request);
}
