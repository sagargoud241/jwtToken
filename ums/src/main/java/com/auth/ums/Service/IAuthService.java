package com.auth.ums.Service;

import com.auth.ums.RequestModel.LoginRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.Auth.LoginResponse;
import com.auth.ums.ResponseModel.user.UserResponse;
import com.auth.ums.configs.ApiResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IAuthService implements AuthService {

    @Autowired
    UserService userService;
    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        var response = userService.login(request);

        if (response != null && response.getCode().equals(ApiResponseCodes.SUCCESS)) {

            ///  jwt tonke ko convcept use grnay

            return ApiResponse.success(null,"login success");
        } else {
            return ApiResponse.failure("Either email or password is wrong");
        }

    }
}
