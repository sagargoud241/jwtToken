package com.auth.ums.Service.PasswordResetService;
import com.auth.ums.JwtSecurity.JwtUtil;
import com.auth.ums.Mapper.PasswordResetMapper;
import com.auth.ums.Models.PasswordReset;
import com.auth.ums.Repository.PasswordResetRepository;
import com.auth.ums.RequestModel.PasswordForgetRequestModel.AddPasswordResetRequest;
import com.auth.ums.RequestModel.PasswordForgetRequestModel.PasswordChangeByToken;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.PasswordReset.PasswordResetResponse;
import com.auth.ums.configs.ÄpiMessageCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    @Autowired
    PasswordResetRepository passwordResetRepository;

    @Autowired
    JwtUtil jwtUtil;


    @Override
    public ApiResponse<PasswordResetResponse> forgetPassword(AddPasswordResetRequest request) {
        PasswordResetResponse response=new PasswordResetResponse();
        try {
            PasswordReset entity=new PasswordReset();
            entity= PasswordResetMapper.addPasswordReset(request);
            entity.setCreatedBy(jwtUtil.getCurrentUsername());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setRequestTime(LocalDateTime.now());
            entity.setIsActive(true);
            entity.setStatus("ACTIVE");
            passwordResetRepository.save(entity);
            response.setDto(PasswordResetMapper.toDTO(entity));
            //entity to dto mapper
            return ApiResponse.success(response,ÄpiMessageCodes.SUCCESSFUL.toString());
        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }

    @Override
    public ApiResponse<PasswordResetResponse> changePasswordByToken(PasswordChangeByToken request) {
        PasswordResetResponse response=new PasswordResetResponse();
        try {
            Optional<PasswordReset> optional=passwordResetRepository.findByTokenAndStatus(request.getToken(),"ACTIVE");
            if(optional.isEmpty()){
                return ApiResponse.failure("Token is not Found");
            }
            LocalDateTime now = LocalDateTime.now();
            PasswordReset entity=optional.get();

            if (entity.getTokenExpirationTime().isBefore(now)) {
                return ApiResponse.failure("Token is expired");
            }

            response.setDto(PasswordResetMapper.toDTO(entity));
            entity.setStatus("INACTIVE");
            entity.setUpdatedBy(jwtUtil.getCurrentUsername());
            entity.setUpdatedDate(LocalDateTime.now());
            passwordResetRepository.save(entity);

            return ApiResponse.success(response,ÄpiMessageCodes.FETCH_SUCCESSFULLY.toString());

        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }
}

