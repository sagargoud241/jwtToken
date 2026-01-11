package com.auth.ums.Repository;

import com.auth.ums.Models.ProfilePicture;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {

    List<ProfilePicture> findByUserIdAndIsCurrentTrue(Long userId);

    @Modifying
    @Query("""
        UPDATE ProfilePicture p
        SET p.isCurrent = false
        WHERE p.user.id = :userId AND p.isCurrent = true
    """)
    void deactivateOldProfilePictures(@Param("userId") Long userId);

    @Query("SELECT p FROM ProfilePicture p WHERE p.user.id = :userId AND p.isCurrent = true")
    ProfilePicture getCurrentProfilePicture(@Param("userId") Long userId);

}
