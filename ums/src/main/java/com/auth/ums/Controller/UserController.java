package com.auth.ums.Controller;
import com.auth.ums.RequestModel.Auth.AddUserRequest;
import com.auth.ums.RequestModel.Auth.DeleteUserRequest;
import com.auth.ums.RequestModel.Auth.UpdateUserRequest;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.user.UserDto;
import com.auth.ums.ResponseModel.user.UserResponse;
import com.auth.ums.ResponseModel.user.UserResponseDto;
import com.auth.ums.Service.UserService.UserService;
import com.auth.ums.Utility.PageResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ResponseEntity<ApiResponse<UserResponse>> adduser(@Valid @RequestBody AddUserRequest request) {
        return ResponseEntity.ok(userService.adduser(request));
    }

    @GetMapping("/list")
    ResponseEntity<ApiResponse<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PutMapping("/update")
    ResponseEntity<ApiResponse<UserResponse>> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(request));
    }

    @DeleteMapping("/delete")
    ResponseEntity<ApiResponse<UserResponse>> deleteUser(@Valid @RequestBody DeleteUserRequest request) {
        return ResponseEntity.ok(userService.deleteUser(request));
    }

    // using UserResponse  only return success it can not return failer
//    @GetMapping
//    public ApiResponse<PageResponse<UserResponseDto>> getUsers(
//            @RequestParam(required = false) String keyword,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id") String sortBy
//    )
//    {
//        return ApiResponse.success(userService.searchUsers(keyword, page, size, sortBy), "Users fetched successfully");
//    }

    @GetMapping("/new")
    public ResponseEntity<ApiResponse<PageResponse<UserDto>>> getUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    )
    {
        return ResponseEntity.ok(userService.searchUsersNew(keyword, page, size, sortBy));

    }
}



