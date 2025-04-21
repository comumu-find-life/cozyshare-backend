package com.api.auth.service;

import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.infra.exception.custom.NotFoundDataException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.api.auth.constants.AuthConstants.NOT_EXIST_REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class TokenCustomService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Transactional
    public void processRefreshToken(String refreshToken, HttpServletResponse response) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundDataException(NOT_EXIST_REFRESH_TOKEN));
        String updateRefreshToken = jwtService.createRefreshToken();
        String updateAccessToken = jwtService.createAccessToken(user.getEmail());
        user.setRefreshToken(updateRefreshToken);
        jwtService.sendAccessAndRefreshToken(response, updateAccessToken, updateRefreshToken);
    }
}