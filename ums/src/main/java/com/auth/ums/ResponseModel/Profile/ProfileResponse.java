package com.auth.ums.ResponseModel.Profile;

import com.auth.ums.ResponseModel.user.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProfileResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dto")
    private UserDto dto;
}
