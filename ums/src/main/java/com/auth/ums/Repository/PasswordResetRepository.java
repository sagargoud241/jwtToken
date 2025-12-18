package com.auth.ums.Repository;
import com.auth.ums.Models.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRepository extends JpaRepository<PasswordReset,Long> {
}
