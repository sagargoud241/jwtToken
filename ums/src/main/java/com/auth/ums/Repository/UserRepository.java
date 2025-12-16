package com.auth.ums.Repository;

import com.auth.ums.Models.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(@NotBlank(message = "Email is Required ") String email);

    Optional<User> findByPhoneNumber(@NotBlank(message = "PhoneNumber is Required ") String phoneNumber);

    Optional<User> findByEmailAndIsActive(@NotBlank(message = "Email is Required ") String email, boolean isActive);
}

