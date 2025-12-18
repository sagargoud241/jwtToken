package com.auth.ums.Service.PasswordResetService;

import com.auth.ums.Repository.PasswordResetRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswordResetServiceImpl implements PasswordResetService {
    @Autowired
    PasswordResetRepository passwordResetRepository;
}
