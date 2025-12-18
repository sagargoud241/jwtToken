package com.auth.ums.ResponseModel.Role;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class RoleResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dto")
    private RoleDTO dto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dtos")
    private List<RoleDTO> dtos;
}
