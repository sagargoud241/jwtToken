package com.auth.ums.Service.ProfllePicture;

import com.auth.ums.JwtSecurity.JwtUtil;
import com.auth.ums.Mapper.ProfilePictureMapper;
import com.auth.ums.Models.ProfilePicture;
import com.auth.ums.Models.User;
import com.auth.ums.Repository.ProfilePictureRepository;
import com.auth.ums.Repository.UserRepository;
import com.auth.ums.ResponseModel.ApiResponse;
import com.auth.ums.ResponseModel.ProfilePicture.ProfilePictureResponse;
import com.auth.ums.Utility.JsonUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class ProfilePictureServiceImpl implements ProfilePictureService {
    private static final Logger log = LoggerFactory.getLogger(ProfilePictureServiceImpl.class);

    @Value("${file.upload.profile-picture-path}")
    private String baseDir;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Override
    public ApiResponse<ProfilePictureResponse> uploadProfilePicture(MultipartFile file) {
        log.info("Profile picture upload started");
        try {
            // 🔐 Get userId from JWT
            Long userId = jwtUtil.getUserIdFromToken(userRepository);
            log.debug("Extracted userId from token: {}", JsonUtils.toJson(userId));
            if (userId == null) {
                log.warn("Invalid JWT token - userId is null");
                return ApiResponse.failure("Invalid token");
            }

            User user = userRepository.findById(userId)

                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 🔄 Deactivate old profile pictures
            profilePictureRepository.deactivateOldProfilePictures(userId);
            log.info("Old profile pictures deactivated for userId {}",JsonUtils.toJson(userId));

            // 📁 Create user folder
            String userDir = baseDir + userId + "/";
            File dir = new File(userDir);
            if (!dir.exists()) dir.mkdirs();

            // 🖼 Save file
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String filePath = userDir + fileName;
            file.transferTo(new File(filePath));
            log.info("Profile picture saved at {}",JsonUtils.toJson(fileName));

            // 💾 Save DB record
            ProfilePicture picture = new ProfilePicture();
            picture.setFileName(fileName);
            picture.setFilePath(filePath);
            picture.setIsCurrent(true);
            picture.setCreatedDate(LocalDateTime.now());
            picture.setIsActive(true);
            picture.setIsDeleted(false);
            picture.setCreatedBy(jwtUtil.getCurrentUsername());
            picture.setUser(user);

            profilePictureRepository.save(picture);
            log.info("Profile picture record saved in DB for userId {}", JsonUtils.toJson(picture));

            // 📦 Response
            ProfilePictureResponse response = new ProfilePictureResponse();
            response.setMessage("Profile picture uploaded successfully");
            response.setProfilePicture(ProfilePictureMapper.toDto(picture));

            return ApiResponse.success(response, "Upload success");

        } catch (Exception e) {
            log.error("Upload failed: {}",e.getMessage(),e);
            return ApiResponse.failure("Profile picture upload failed");
        }
    }

    @Override
    public ApiResponse<ProfilePictureResponse> deleteProfilePicture() {
        log.info("🗑 PROFILE DELETE STARTED");

        try {
            Long userId = jwtUtil.getUserIdFromToken(userRepository);
            log.info("JWT extracted userId = {}", userId);

            if (userId == null) {
                return ApiResponse.failure("Invalid token");
            }

            ProfilePicture picture = profilePictureRepository.getCurrentProfilePicture(userId);

            // ❌ No active profile picture
            if (picture == null) {
                log.warn("No active profile picture found for userId={}", userId);
                return ApiResponse.failure("Profile picture already deleted");
            }

            // 🗑 Delete file
            File file = new File(picture.getFilePath());
            if (file.exists()) {
                file.delete();
            }

            // 🔄 Mark as false
            picture.setIsCurrent(false);
            picture.setIsDeleted(true);
            picture.setIsActive(false);
            picture.setUpdatedDate(LocalDateTime.now());
            picture.setUpdatedBy(jwtUtil.getCurrentUsername());
            profilePictureRepository.save(picture);

            log.info("🗑 PROFILE DELETE SUCCESS :{}",JsonUtils.toJson(picture));
            return ApiResponse.success(null,"Profile picture deleted successfully");

        } catch (Exception e) {
            log.error("Delete error :{}",e.getMessage(),e);
            return ApiResponse.failure("Delete failed");
        }
    }
}
