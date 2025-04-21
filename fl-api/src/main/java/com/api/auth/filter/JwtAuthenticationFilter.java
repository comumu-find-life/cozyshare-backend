package com.api.auth.filter;

import com.api.exception.ErrorResponse;
import com.api.auth.service.JwtService;
import com.api.auth.service.TokenCustomService;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.infra.exception.custom.AuthException;
import com.infra.exception.ErrorResponseCode;
import com.infra.exception.custom.NotFoundDataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/login";

    private final TokenCustomService tokenCustomService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Optional<String> refreshToken = jwtService.extractRefreshToken(request);
            if (refreshToken.isPresent()) {
                checkRefreshTokenAndReIssueAccessToken(refreshToken.get(), request, response);
                return;
            }
            if (refreshToken.isEmpty()) {
                checkAccessTokenAndAuthentication(request, response, filterChain);
            }
        } catch (AuthException ex) {
            handleException(response, ex);
        }
    }


    public void checkRefreshTokenAndReIssueAccessToken(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        try {
            tokenCustomService.processRefreshToken(refreshToken, response);
        } catch (Exception e) {
            throw new NotFoundDataException(e.getMessage());
        }
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .ifPresent(accessToken -> {
                    try {
                        // isTokenValid 호출 시 예외 처리
                        jwtService.isTokenValid(accessToken);
                        jwtService.extractEmail(accessToken)
                                .ifPresent(email -> userRepository.findByEmail(email)
                                        .ifPresent(this::saveAuthentication));
                    } catch (Exception e) {
                        throw new AuthException(ErrorResponseCode.NOT_VALID_TOKEN, "유효하지 않는 토큰입니다.");
                    }
                });

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(User myUser) {
        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .roles("Role")
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleException(HttpServletResponse response, AuthException ex) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResponse(ex)));
    }
}
