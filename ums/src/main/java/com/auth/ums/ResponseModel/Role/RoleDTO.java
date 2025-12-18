package com.auth.ums.ResponseModel.Role;

import com.auth.ums.ResponseModel.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO extends BaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

}
