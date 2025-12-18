package com.auth.ums.Service.UserRoleService;


import com.auth.ums.Mapper.UserRoleMapper;
import com.auth.ums.Models.Role;
import com.auth.ums.Models.User;
import com.auth.ums.Models.UserRole;
import com.auth.ums.Repository.RoleRepository;
import com.auth.ums.Repository.UserRepository;
import com.auth.ums.Repository.UserRoleRepository;
import com.auth.ums.RequestModel.UserRoleRequestModel.AddUserRoleRequest;
import com.auth.ums.RequestModel.UserRoleRequestModel.DeleteUserRoleRequest;
import com.auth.ums.RequestModel.UserRoleRequestModel.UpdateUserRoleRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.UserRole.UserRoleResponse;
import com.auth.ums.Utility.Utility;
import com.auth.ums.configs.ÄpiMessageCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public ApiResponse<UserRoleResponse> addUserRole(AddUserRoleRequest request) {
        UserRoleResponse response = new UserRoleResponse();
        try {
            Optional<User> optionalUserId = userRepository.findById(request.getUserId());
            if (optionalUserId.isEmpty()) {
                return ApiResponse.failure("User id Not found ");
            }

            Optional<Role> optionalRoleId = roleRepository.findById(request.getRoleId());
            if (optionalRoleId.isEmpty()) {
                return ApiResponse.failure("Role id Not found ");
            }

            UserRole entity = new UserRole();
            entity = UserRoleMapper.addUserRole(request);
            entity.setCreatedBy(Utility.getDefaultUsername());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setIsActive(true);
            entity.setIsDeleted(false);
            userRoleRepository.save(entity);
            response.setDto(UserRoleMapper.toDto(entity));
            return ApiResponse.success(response, ÄpiMessageCodes.CREATED_SUCCESSFULLY.toString());

        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }

    @Override
    public ApiResponse<UserRoleResponse> updateUserRole(UpdateUserRoleRequest request) {
        UserRoleResponse response = new UserRoleResponse();
        try {
            Optional<UserRole> optional= userRoleRepository.findById(request.getUserId());
            if (optional.isEmpty()) {
                return ApiResponse.failure("UserRole id Not found ");
            }

            Optional<User> optionalUserId = userRepository.findById(request.getUserId());
            if (optionalUserId.isEmpty()) {
                return ApiResponse.failure("User id Not found ");
            }
            Optional<Role> optionalRoleId = roleRepository.findById(request.getRoleId());
            if (optionalRoleId.isEmpty()) {
                return ApiResponse.failure("Role id Not found ");
            }
            Optional<UserRole>optionalid = userRoleRepository.findById(request.getId());
            if (optionalid.get().getIsDeleted()) {
                return ApiResponse.failure("UserRole id is not found");
            }
            UserRole entity= optional.get();
            entity = UserRoleMapper.updateUserRole(entity,request);
            entity.setUpdatedBy(Utility.getDefaultUsername());
            entity.setUpdatedDate(LocalDateTime.now());
            userRoleRepository.save(entity);
            response.setDto(UserRoleMapper.toDto(entity));
            return ApiResponse.success(response, ÄpiMessageCodes.UPDATED_SUCCESSFULLY.toString());
        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }

    @Override
    public ApiResponse<UserRoleResponse> deleteUserRole(DeleteUserRoleRequest request) {
        UserRoleResponse response = new UserRoleResponse();
        try {
            // check if role name already exists or not
            Optional<UserRole>optional = userRoleRepository.findById(request.getId());
            if (optional.isEmpty()) {
                return ApiResponse.failure("UserRole id is not found");
            }
            UserRole entity = optional.get();
            entity = UserRoleMapper.deleteUserRole(entity,request);
            entity.setUpdatedBy(Utility.getDefaultUsername());
            entity.setUpdatedDate(LocalDateTime.now());
            entity.setIsActive(false);
            entity.setIsDeleted(true);
            userRoleRepository.save(entity);
            response.setDto(UserRoleMapper.toDto(entity));

            return ApiResponse.success(response, ÄpiMessageCodes.DELETED_SUCCESSFULLY.toString());
        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }

    @Override
    public ApiResponse<UserRoleResponse> getAllUserRole() {

        UserRoleResponse response = new UserRoleResponse();
        try {
            List<UserRole> userRoles = userRoleRepository.findAll();
            if (userRoles.isEmpty()) {
                return ApiResponse.failure(ÄpiMessageCodes.NO_RESULT_FOUND.toString());
            }
            response.setDtos(UserRoleMapper.toDTOList(userRoles));
            return ApiResponse.success(response,ÄpiMessageCodes.FETCH_SUCCESSFULLY.toString());

        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }

}

