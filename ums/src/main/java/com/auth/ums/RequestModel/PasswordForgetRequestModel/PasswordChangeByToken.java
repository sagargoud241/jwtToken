package com.auth.ums.RequestModel.PasswordForgetRequestModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordChangeByToken {
    @NotBlank(message = "New PassWord is Required ")
    @JsonProperty("new_Password")
    private String newPassword;

    @NotBlank(message = "Conform PassWord is Required ")
    @JsonProperty("conform_Password")
    private String conformPassword;

    @NotBlank(message = "Token is Required ")
    @JsonProperty("token")
    private String token;
}
