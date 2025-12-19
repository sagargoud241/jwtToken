package com.auth.ums.Service.RefreshTokenService;

import com.auth.ums.RequestModel.RefreshTokenRequestModel.AddRefreshTokenRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.RefreshToken.RefreshTokenResponse;

public interface RefreshTokenService {
    ApiResponse<RefreshTokenResponse> addRefreshToken(AddRefreshTokenRequest request);
    ApiResponse<RefreshTokenResponse> findByRefreshToken(String tokenHash);
}
