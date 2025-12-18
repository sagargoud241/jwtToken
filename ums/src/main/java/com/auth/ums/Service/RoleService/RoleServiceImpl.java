package com.auth.ums.Service.RoleService;

import com.auth.ums.Mapper.RoleMapper;
import com.auth.ums.Models.Role;
import com.auth.ums.Repository.RoleRepository;
import com.auth.ums.RequestModel.RoleRequestModel.AddRoleRequest;
import com.auth.ums.RequestModel.RoleRequestModel.DeleteRoleRequest;
import com.auth.ums.RequestModel.RoleRequestModel.UpdateRoleRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.Role.RoleResponse;
import com.auth.ums.Utility.Utility;
import com.auth.ums.configs.ÄpiMessageCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public ApiResponse<RoleResponse> addRole(AddRoleRequest request) {

        RoleResponse response = new RoleResponse();

        try {
            // check if role name already exists or not
            Optional<Role> optional = roleRepository.findByName(request.getName());
            if (optional.isPresent()) {
                return ApiResponse.failure("UserName is Already Used");
            }
            Role entity = new Role();
            entity = RoleMapper.addRole(request);
            entity.setCreatedBy(Utility.getDefaultUsername());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setIsActive(true);
            entity.setIsDeleted(false);
            roleRepository.save(entity);
            response.setDto(RoleMapper.toDto(entity));
            return ApiResponse.success(response, ÄpiMessageCodes.CREATED_SUCCESSFULLY.toString());
        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }

    }

    @Override
    public ApiResponse<RoleResponse> updateRole(UpdateRoleRequest request) {
        RoleResponse response = new RoleResponse();
        try {
            // check if role name already exists or not
            Optional<Role> optional = roleRepository.findByName(request.getName());
            if (optional.isPresent()) {
                //check the id is duplicate or not
                if (!optional.get().getId().equals(request.getId())) {
                    return ApiResponse.failure(ÄpiMessageCodes.DUPLICATE_DATA_FOUND.toString());
                }
            }
            Optional<Role> model = roleRepository.findById(request.getId());
            if (model == null || model.isEmpty()) {
                return ApiResponse.failure(ÄpiMessageCodes.NO_RESULT_FOUND.toString());
            }
            if (model.get().getIsDeleted()) {
                return ApiResponse.failure(ÄpiMessageCodes.NO_RESULT_FOUND.toString());
            }
            Role entity = model.get();
            entity = RoleMapper.updateRole(entity, request);
            entity.setUpdatedBy(Utility.getDefaultUsername());
            entity.setUpdatedDate(LocalDateTime.now());
            roleRepository.save(entity);
            response.setDto(RoleMapper.toDto(entity));
            return ApiResponse.success(response, ÄpiMessageCodes.UPDATED_SUCCESSFULLY.toString());
        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }

    }

    @Override
    public ApiResponse<RoleResponse> getRoleById(Long id) {
        RoleResponse response = new RoleResponse();
        try {
            Optional<Role> optional = roleRepository.findById(id);
            if (optional.isEmpty()) {
                return ApiResponse.failure("Role Id is not Found");
            }
            if (optional.get().getIsDeleted() == true) {
                return ApiResponse.failure("Role Id is not Found");
            }
            Role entity = optional.get();
            response.setDto(RoleMapper.toDto(entity));
            return ApiResponse.success(response, ÄpiMessageCodes.FETCH_SUCCESSFULLY.toString());
        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }

    @Override
    public ApiResponse<RoleResponse> getAllRoll() {
        RoleResponse response = new RoleResponse();
        try {
            List<Role> roles = roleRepository.findAll();

            if (roles.isEmpty()) {
                return ApiResponse.failure(ÄpiMessageCodes.NO_RESULT_FOUND.toString());
            }
            response.setDtos(RoleMapper.toDTOList(roles));
            return ApiResponse.success(response, ÄpiMessageCodes.FETCH_SUCCESSFULLY.toString());
        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }

    @Override
    public ApiResponse<RoleResponse> deleteRole(DeleteRoleRequest request) {

        RoleResponse response = new RoleResponse();
        try {
            // check if role name already exists or not
            Optional<Role> optional = roleRepository.findById(request.getId());
            if (optional.isEmpty()) {
                return ApiResponse.failure("Role id is not found");
            }
            Role entity = optional.get();
            entity = RoleMapper.deleteRole(entity, request);
            entity.setUpdatedBy(Utility.getDefaultUsername());
            entity.setUpdatedDate(LocalDateTime.now());
            entity.setIsActive(false);
            entity.setIsDeleted(true);
            roleRepository.save(entity);
            response.setDto(RoleMapper.toDto(entity));
            return ApiResponse.success(response, ÄpiMessageCodes.DELETED_SUCCESSFULLY.toString());
        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }

    @Override
    public ApiResponse<RoleResponse> getRoleByName(String name) {
        RoleResponse response = new RoleResponse();
        try {
            Optional<Role> optional = roleRepository.findByName(name);
            if (optional.isEmpty()) {
                return ApiResponse.failure("Role Id is not Found");
            }
            if (optional.get().getIsDeleted() == true) {
                return ApiResponse.failure("Role Id is not Found");
            }
            Role entity = optional.get();
            response.setDto(RoleMapper.toDto(entity));
            return ApiResponse.success(response, ÄpiMessageCodes.FETCH_SUCCESSFULLY.toString());
        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }
}