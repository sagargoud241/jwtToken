package com.auth.ums.Mapper;

import com.auth.ums.Models.ProfilePicture;
import com.auth.ums.ResponseModel.ProfilePicture.ProfilePictureDTO;

public class ProfilePictureMapper {

    public static ProfilePictureDTO toDto(ProfilePicture entity) {

        ProfilePictureDTO dto = new ProfilePictureDTO();
        dto.setId(entity.getId());
        dto.setFileName(entity.getFileName());
        dto.setFilePath(entity.getFilePath());
        dto.setIsCurrent(entity.getIsCurrent());

        return dto;
    }
}
