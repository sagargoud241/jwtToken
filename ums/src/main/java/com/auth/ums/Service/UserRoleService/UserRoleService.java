package com.auth.ums.Service.UserRoleService;

import com.auth.ums.RequestModel.UserRoleRequestModel.AddUserRoleRequest;
import com.auth.ums.RequestModel.UserRoleRequestModel.DeleteUserRoleRequest;
import com.auth.ums.RequestModel.UserRoleRequestModel.UpdateUserRoleRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.UserRole.UserRoleResponse;

public interface UserRoleService {
    ApiResponse<UserRoleResponse>addUserRole(AddUserRoleRequest request);
    ApiResponse<UserRoleResponse>updateUserRole(UpdateUserRoleRequest request);
    ApiResponse<UserRoleResponse>deleteUserRole(DeleteUserRoleRequest request);
    ApiResponse<UserRoleResponse>getAllUserRole();

}
