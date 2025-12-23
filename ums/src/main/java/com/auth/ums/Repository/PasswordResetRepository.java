package com.auth.ums.Repository;

import com.auth.ums.Models.PasswordReset;
import com.auth.ums.ResponseModel.PasswordReset.PasswordResetResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
    Optional<PasswordResetResponse> findByToken(@NotBlank(message = "Token is Required ") String token);

    Optional<PasswordReset> findByTokenAndStatus(@NotBlank(message = "Token is Required ") String token, String status);
}
