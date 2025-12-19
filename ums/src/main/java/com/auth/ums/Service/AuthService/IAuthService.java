package com.auth.ums.Service.AuthService;

import com.auth.ums.JwtSecurity.JwtUtil;
import com.auth.ums.RequestModel.LoginRequest;
import com.auth.ums.RequestModel.RefreshTokenRequestModel.AddRefreshTokenRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.Auth.LoginResponse;
import com.auth.ums.ResponseModel.Role.RoleDTO;
import com.auth.ums.Service.RefreshTokenService.RefreshTokenService;
import com.auth.ums.Service.RoleService.RoleService;
import com.auth.ums.Service.UserService.UserService;
import com.auth.ums.Utility.JsonUtils;
import com.auth.ums.configs.ApiResponseCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IAuthService implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(IAuthService.class);
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    RoleService roleService;
    @Autowired
    RefreshTokenService refreshTokenService;

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {

        LoginResponse authResponse = new LoginResponse();
        var response = userService.login(request);

        log.info("user info : {}", JsonUtils.toJson(response));

        if (response != null && response.getCode().equals(ApiResponseCodes.SUCCESS)) {

            ///  jwt tonke ko convcept use grnay

            // Extract roles
            List<String> roles = null;
            var userRoles = roleService.geUserRoleByUserId(response.getData().getUser().getId());

            log.info("user userRoles data : {}", JsonUtils.toJson(userRoles));

            if (userRoles != null && userRoles.getCode().equals(ApiResponseCodes.SUCCESS)) {

                roles = userRoles.getData().getDtos().stream().map(RoleDTO::getName).collect(Collectors.toList());

                log.info("user  roles  : {}", JsonUtils.toJson(roles));

            }

            // Generate tokens
            String accessToken = jwtUtil.generateAccessToken(response.getData().getUser().getEmail(), roles);
            String refreshToken = jwtUtil.generateRefreshToken(response.getData().getUser().getEmail());
            authResponse.setToken(accessToken);
            authResponse.setRefreshToken(refreshToken);

            String formattedExpiryTime = jwtUtil.getExpiryDateTimeFormatted(accessToken, "yyyy-MM-dd HH:mm:ss");
            authResponse.setTokenExpiryTime(formattedExpiryTime);
            authResponse.setUser(response.getData().getUser());

            // save refresh_token
            AddRefreshTokenRequest refTok = new AddRefreshTokenRequest();

            refTok.setUserId(response.getData().getUser().getId());
            refTok.setTokenHash(refreshToken);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(formattedExpiryTime, formatter);
            refTok.setExpiresAt(localDateTime);
            refreshTokenService.addRefreshToken(refTok);

            return ApiResponse.success(authResponse, "login success");

        } else {
            return ApiResponse.failure("Either email or password is wrong");
        }

    }

    @Override
    public ApiResponse<LoginResponse> refreshToken(String tokenHash) {

        LoginResponse authResponse = new LoginResponse();
        var response = refreshTokenService.findByRefreshToken(tokenHash);

        log.info("refresh Token info : {}", JsonUtils.toJson(response));

        if (response != null && response.getCode().equals(ApiResponseCodes.SUCCESS)) {

            ///  jwt tonke ko convcept use grnay

            // Extract roles
            List<String> roles = null;
            var userRoles = roleService.geUserRoleByUserId(response.getData().getDto().getUserId());

            log.info("user userRoles data : {}", JsonUtils.toJson(userRoles));

            if (userRoles != null && userRoles.getCode().equals(ApiResponseCodes.SUCCESS)) {

                roles = userRoles.getData().getDtos().stream().map(RoleDTO::getName).collect(Collectors.toList());

                log.info("user  roles  : {}", JsonUtils.toJson(roles));

            }

            //get user info by user id

            var userData=userService.findByUserId(response.getData().getDto().getUserId());

            if(userData!=null&& userData.getCode().equals(ApiResponseCodes.SUCCESS)){
                // Generate tokens
                String accessToken = jwtUtil.generateAccessToken(userData.getData().getUser().getEmail(), roles);
                String refreshToken = jwtUtil.generateRefreshToken(userData.getData().getUser().getEmail());
                authResponse.setToken(accessToken);
                authResponse.setRefreshToken(refreshToken);

                String formattedExpiryTime = jwtUtil.getExpiryDateTimeFormatted(accessToken, "yyyy-MM-dd HH:mm:ss");
                authResponse.setTokenExpiryTime(formattedExpiryTime);
                authResponse.setUser(userData.getData().getUser());

                // save refresh_token
                AddRefreshTokenRequest refTok = new AddRefreshTokenRequest();

                refTok.setUserId(userData.getData().getUser().getId());
                refTok.setTokenHash(refreshToken);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(formattedExpiryTime, formatter);
                refTok.setExpiresAt(localDateTime);
                refreshTokenService.addRefreshToken(refTok);
                return ApiResponse.success(authResponse, "refreshed  successfully");
            }else{
                return ApiResponse.failure("Invalid user");
            }

        } else {
            return ApiResponse.failure("Invalid Token");
        }
    }
}
