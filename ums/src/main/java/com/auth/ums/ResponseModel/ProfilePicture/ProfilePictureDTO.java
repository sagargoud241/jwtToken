package com.auth.ums.ResponseModel.ProfilePicture;

import com.auth.ums.ResponseModel.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProfilePictureDTO extends BaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_path")
    private String filePath;

    @JsonProperty("is_current")
    private Boolean isCurrent;
}
