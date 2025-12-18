package com.auth.ums.Service.RefreshTokenService;

import com.auth.ums.Repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
}
