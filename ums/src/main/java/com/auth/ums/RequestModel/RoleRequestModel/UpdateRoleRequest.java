package com.auth.ums.RequestModel.RoleRequestModel;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRoleRequest {
    private Long id;

    @NotBlank(message = "Name is Required ")
    private String name;
}
