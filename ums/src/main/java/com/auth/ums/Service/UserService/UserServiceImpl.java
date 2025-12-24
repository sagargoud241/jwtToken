package com.auth.ums.Service.UserService;

import com.auth.ums.JwtSecurity.JwtUtil;
import com.auth.ums.Mapper.UserMapper;
import com.auth.ums.Models.User;
import com.auth.ums.Repository.UserRepository;
import com.auth.ums.RequestModel.Auth.AddUserRequest;
import com.auth.ums.RequestModel.Auth.DeleteUserRequest;
import com.auth.ums.RequestModel.Auth.LoginRequest;
import com.auth.ums.RequestModel.Auth.UpdateUserRequest;
import com.auth.ums.RequestModel.PasswordForgetRequestModel.PasswordChangeByToken;
import com.auth.ums.RequestModel.ProfileModel.ChangePasswordRequest;
import com.auth.ums.RequestModel.UserRoleRequestModel.AddUserRoleRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.user.UserResponse;
import com.auth.ums.Service.RoleService.RoleService;
import com.auth.ums.Service.UserRoleService.UserRoleService;
import com.auth.ums.Utility.Utility;
import com.auth.ums.configs.ApiResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleService userRoleService;

    @Autowired
    JwtUtil jwtUtil;
    @Override
    public ApiResponse<UserResponse> adduser(AddUserRequest request) {

     //   UserResponse response = new UserResponse();
        Optional<User> optionalEmail = userRepository.findByEmail(request.getEmail());

        if (optionalEmail != null && !optionalEmail.isEmpty()) {
            return ApiResponse.failure("Email is Already Used");
        }
        Optional<User> optionalPhoneNumber = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (optionalPhoneNumber != null && !optionalPhoneNumber.isEmpty()) {
            return ApiResponse.failure("PhoneNumber is Already Used");
        }
        User user = UserMapper.addUser(request);
        user.setCreatedBy(Utility.getDefaultUsername());
        user.setCreatedDate(LocalDateTime.now());
        user.setIsActive(true);
        user.setIsDeleted(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    //    response.setUser(UserMapper.toUserDTO(user));
        userRepository.save(user);

        ///  add GUEST Role to the user
          var roleResponse=roleService.getRoleByName("GUEST");
          if(roleResponse!=null &&roleResponse.getCode().equals(ApiResponseCodes.SUCCESS)){
              AddUserRoleRequest addRole=new AddUserRoleRequest();
              addRole.setRoleId(roleResponse.getData().getDto().getId());
              addRole.setUserId(user.getId());
              userRoleService.addUserRole(addRole);
          }
        ///

        return ApiResponse.success(null, "AddSuccessFully");
    }

    @Override
    public ApiResponse<UserResponse> login(LoginRequest request) {
        {
            UserResponse response=new UserResponse();
            Optional<User> optional = userRepository.findByEmailAndIsActive(request.getEmail(), true);
            if (optional.isEmpty()) {
                return ApiResponse.failure("User not Found");
            }
            User user = optional.get();
            if (user.getIsDeleted()) {
                return ApiResponse.failure("User not found");
            }
            if (passwordEncoder.matches(request.getPassword(), optional.get().getPassword())) {
                response.setUser(UserMapper.toUserDTO(user));
                return ApiResponse.success(response, "Fetch SuccessFully");
            } else {
                return ApiResponse.failure("User not Found");
            }
        }
    }

    @Override
    public ApiResponse<UserResponse> findByUserId(Long id) {
        UserResponse response = new UserResponse();
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            return ApiResponse.failure("User not Found");
        }
        User user = optional.get();
        if (user.getIsDeleted()) {
            return ApiResponse.failure("User not found");
        }
        response.setUser(UserMapper.toUserDTO(user));
        return ApiResponse.success(response, "Fetch SuccessFully");
    }

    @Override
    public ApiResponse<UserResponse> changePasswordByLoggedInUser(String userName, ChangePasswordRequest request) {


        UserResponse response = new UserResponse();
        Optional<User> optional = userRepository.findByEmailAndIsActive(userName, true);
        if (optional.isEmpty()) {
            return ApiResponse.failure("User not Found");
        }
        User user = optional.get();
        if (user.getIsDeleted()) {
            return ApiResponse.failure("User not found");
        }
        if (passwordEncoder.matches(request.getOldPassword(), optional.get().getPassword())) {

            /// set new password
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            user.setUpdatedBy(jwtUtil.getCurrentUsername());
            user.setUpdatedDate(LocalDateTime.now());
            userRepository.save(user);

            response.setUser(UserMapper.toUserDTO(user));
            return ApiResponse.success(response, "Password Updated Successfully");

        } else {
            return ApiResponse.failure("User not Found");
        }

    }

    @Override
    public ApiResponse<UserResponse> findByUserName(String userName) {
        UserResponse response = new UserResponse();
        Optional<User> optional = userRepository.findByEmailAndIsActive(userName, true);
        if (optional.isEmpty()) {
            return ApiResponse.failure("User not Found");
        }
        User user = optional.get();
        if (user.getIsDeleted()) {
            return ApiResponse.failure("User not found");
        }
        response.setUser(UserMapper.toUserDTO(user));
        return ApiResponse.success(response, "Fetch SuccessFully");
    }

    @Override
    public ApiResponse<UserResponse> passwordChangeByToken(Long userId, PasswordChangeByToken request) {
        UserResponse response = new UserResponse();
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) {
            return ApiResponse.failure("User not Found");
        }
        User user = optional.get();
        if (user.getIsDeleted()) {
            return ApiResponse.failure("User not found");
        }
        /// set new password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedBy(jwtUtil.getCurrentUsername());
        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);
        response.setUser(UserMapper.toUserDTO(user));
        return ApiResponse.success(response, "Password Updated Successfully");
    }


    @Override
    public ApiResponse<UserResponse> getAllUser() {

        try {
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                return ApiResponse.failure("No User List");
            }

            UserResponse response = new UserResponse();
            response.setUsers(
                    users.stream()
                            .map(UserMapper::toUserDTO)
                            .toList()
            );

            return ApiResponse.success(response, "Fetch successfully");

        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }

    @Override
    public ApiResponse<UserResponse> updateUser(UpdateUserRequest request) {
        UserResponse response = new UserResponse();
        try {
            Optional<User> optional = userRepository.findById(request.getId());
            if (optional.isEmpty()) {
                return ApiResponse.failure("User not Found");
            }
            if (optional.get().getIsDeleted()) {
                return ApiResponse.failure("User not Found");
            }
            User user = optional.get();
            user = UserMapper.updateUser(user, request);
            user.setIsActive(Boolean.TRUE);
            user.setUpdatedDate(LocalDateTime.now());
            user.setUpdatedBy(jwtUtil.getCurrentUsername());
            userRepository.save(user);
            response.setUser(UserMapper.toUserDTO(user));
            return ApiResponse.success(response, "Update SuccessFully");
        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }

    }

    @Override
    public ApiResponse<UserResponse> deleteUser(DeleteUserRequest request) {
        UserResponse response = new UserResponse();
        try {
            Optional<User> optional = userRepository.findById(request.getId());
            if (optional.isEmpty()) {
                return ApiResponse.failure("User not Found");
            }
            if (optional.get().getIsDeleted()) {
                return ApiResponse.failure("User not Found");
            }
            User user = optional.get();
            user = UserMapper.deleteUser(user, request);
            user.setIsActive(Boolean.FALSE);
            user.setIsDeleted(true);
            user.setUpdatedDate(LocalDateTime.now());
            user.setUpdatedBy(jwtUtil.getCurrentUsername());
            userRepository.save(user);
            response.setUser(UserMapper.toUserDTO(user));
            return ApiResponse.success(response, "Delete SuccessFully");
        } catch (Exception e) {
            return ApiResponse.exception(e.getMessage());
        }
    }

}
