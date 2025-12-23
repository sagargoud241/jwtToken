package com.auth.ums.RequestModel.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgetPasswordRequest {
    @NotBlank(message = "User name is Required ")
    @JsonProperty("user_name")
    private String userName;
}
