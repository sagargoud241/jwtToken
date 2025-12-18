package com.auth.ums.RequestModel.UserRoleRequestModel;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRoleRequest {

    private Long id;

    @NotBlank(message = "UserId is Required ")
    private Long userId;

    @NotBlank(message = "RoleId is Required ")
    private Long roleId;
}
