package com.auth.ums.Service.RoleService;

import com.auth.ums.RequestModel.RoleRequestModel.AddRoleRequest;
import com.auth.ums.RequestModel.RoleRequestModel.DeleteRoleRequest;
import com.auth.ums.RequestModel.RoleRequestModel.UpdateRoleRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.Role.RoleResponse;
import com.auth.ums.ResponseModel.UserRole.UserRoleResponse;

public interface RoleService {

   ApiResponse<RoleResponse>addRole(AddRoleRequest request);
   ApiResponse<RoleResponse>updateRole(UpdateRoleRequest request);
   ApiResponse<RoleResponse>getRoleById(Long id);
   ApiResponse<RoleResponse>getAllRoll();
   ApiResponse<RoleResponse>deleteRole(DeleteRoleRequest request);
   ApiResponse<RoleResponse>getRoleByName(String name);
    ApiResponse<RoleResponse>geUserRoleByUserId(Long userId);
}
