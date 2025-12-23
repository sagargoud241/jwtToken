package com.auth.ums.ResponseModel.Auth;

import com.auth.ums.ResponseModel.PasswordReset.PasswordResetDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ForgetPasswordResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dto")
    private PasswordResetDTO dto;
}
