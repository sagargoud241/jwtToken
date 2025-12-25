package com.auth.ums.Service.RefreshTokenService;

import com.auth.ums.Mapper.RefreshTokenMapper;
import com.auth.ums.Models.RefreshToken;
import com.auth.ums.Repository.RefreshTokenRepository;
import com.auth.ums.RequestModel.RefreshTokenRequestModel.AddRefreshTokenRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.RefreshToken.RefreshTokenResponse;
import com.auth.ums.Utility.JsonUtils;
import com.auth.ums.configs.ÄpiMessageCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private static final Logger log = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Override
    public ApiResponse<RefreshTokenResponse> addRefreshToken(AddRefreshTokenRequest request) {
        log.info("Request received to add refresh token for userId: {}", JsonUtils.toJson(request));
        try {
            RefreshTokenResponse response = new RefreshTokenResponse();
            RefreshToken entity = new RefreshToken();
            entity = RefreshTokenMapper.addRefreshToken(request);
            entity.setCreatedDate(LocalDateTime.now());
            entity.setIsActive(true);
            refreshTokenRepository.save(entity);
            log.info("Refresh token saved successfully with tokenHash: {}", JsonUtils.toJson(entity));
            response.setDto(RefreshTokenMapper.toDTO(entity));
            return ApiResponse.success(response, ÄpiMessageCodes.CREATED_SUCCESSFULLY.toString());
        } catch (Exception e) {
            log.error("Error occurred while adding refresh token:{}", e.getMessage(), e);
            return ApiResponse.exception(e.getMessage());
        }
    }

    @Override
    public ApiResponse<RefreshTokenResponse> findByRefreshToken(String tokenHash) {
        log.info("Searching refresh token with tokenHash: {}", JsonUtils.toJson(tokenHash));
        try {
            RefreshTokenResponse response = new RefreshTokenResponse();
            Optional<RefreshToken> optional = refreshTokenRepository.findByTokenHash(tokenHash);

            if (optional.isEmpty()) {
                log.warn("Refresh token not found for tokenHash: {}", JsonUtils.toJson(tokenHash));
                return ApiResponse.failure("Refresh Token  is not Found");
            }
            RefreshToken entity = optional.get();

            response.setDto(RefreshTokenMapper.toDTO(entity));

            entity.setRevoked(true);
            entity.setIsActive(false);

            refreshTokenRepository.save(entity);

            return ApiResponse.success(response, ÄpiMessageCodes.DATA_RETRIEVED_SUCCESSFULLY.toString());

        } catch (Exception e) {
            log.error("Error occurred while fetching refresh token with tokenHash: {}", e.getMessage(), e);
            return ApiResponse.exception(e.getMessage());
        }
    }
}
