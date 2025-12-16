package com.auth.ums.Service;

import com.auth.ums.Mapper.UserMapper;
import com.auth.ums.Models.User;
import com.auth.ums.Repository.UserRepository;
import com.auth.ums.RequestModel.AddUserRequest;
import com.auth.ums.RequestModel.LoginRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.user.UserResponse;
import com.auth.ums.Utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;
    @Override
    public ApiResponse<User> adduser(AddUserRequest request) {
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
        userRepository.save(user);
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
}
