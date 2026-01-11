package com.auth.ums.Service.ProfllePicture;

import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.ProfilePicture.ProfilePictureResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProfilePictureService {
    ApiResponse<ProfilePictureResponse> uploadProfilePicture(MultipartFile file);
    ApiResponse<ProfilePictureResponse> deleteProfilePicture();

}
