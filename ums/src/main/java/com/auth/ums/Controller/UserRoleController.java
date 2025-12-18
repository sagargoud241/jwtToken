package com.auth.ums.Controller;

import com.auth.ums.RequestModel.UserRoleRequestModel.AddUserRoleRequest;
import com.auth.ums.RequestModel.UserRoleRequestModel.DeleteUserRoleRequest;
import com.auth.ums.RequestModel.UserRoleRequestModel.UpdateUserRoleRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.UserRole.UserRoleResponse;
import com.auth.ums.Service.UserRoleService.UserRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-role")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;
    @PostMapping("add-user-role")
    ResponseEntity<ApiResponse<UserRoleResponse>> addUserRole(@Valid @RequestBody AddUserRoleRequest request){
        return ResponseEntity.ok(userRoleService.addUserRole(request));
    }

    @PutMapping("update-user-role")
    ResponseEntity<ApiResponse<UserRoleResponse>> updateUserRole(@Valid @RequestBody UpdateUserRoleRequest request){
        return ResponseEntity.ok(userRoleService.updateUserRole(request));
    }

    @DeleteMapping("delete-user-role")
    ResponseEntity<ApiResponse<UserRoleResponse>> deleteUserRole(@Valid @RequestBody DeleteUserRoleRequest request){
        return ResponseEntity.ok(userRoleService.deleteUserRole(request));
    }

    @GetMapping("get-all-user-role")
    ResponseEntity<ApiResponse<UserRoleResponse>> getAllUserRole(){
        return ResponseEntity.ok(userRoleService.getAllUserRole());
    }

}


