package com.auth.ums.Repository;

import com.auth.ums.Models.Role;
import com.auth.ums.Models.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(@NotBlank(message = "Name is Required ") String name);


}
