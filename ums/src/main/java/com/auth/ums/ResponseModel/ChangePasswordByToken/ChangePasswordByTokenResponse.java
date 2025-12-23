package com.auth.ums.ResponseModel.ChangePasswordByToken;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordByTokenResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dto")
    private ChangePasswordByTokenDTO dto;
}
