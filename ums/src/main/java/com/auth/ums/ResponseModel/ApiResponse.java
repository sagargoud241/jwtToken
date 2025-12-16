package com.auth.ums.ResponseModel;

import com.auth.ums.configs.ApiResponseCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse <T>{
    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ApiResponseCodes code;

    private String message;

    private T data;


    // --- Static factory methods ---
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(ApiResponseCodes.SUCCESS, message, data);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(ApiResponseCodes.FAILURE, message, null);
    }

    public static <T> ApiResponse<T> exception(String message) {
        return new ApiResponse<>(ApiResponseCodes.INVALID_REQUEST, message,null);
    }


    // --- Builder Pattern ---
    public static class Builder<T> {

        @NotNull
        @Enumerated(EnumType.STRING)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private ApiResponseCodes code;
        private String message;
        private T data;


        public ApiResponse<T> build() {
            return new ApiResponse<>(code, message, data);
        }
    }


}
