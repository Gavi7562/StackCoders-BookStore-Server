package com.stackcoders.bookstore.service.impl;

import com.stackcoders.bookstore.constants.Role;
import com.stackcoders.bookstore.constants.SecurityConstants;
import com.stackcoders.bookstore.dto.request.LoginRequest;
import com.stackcoders.bookstore.dto.request.RegisterRequest;
import com.stackcoders.bookstore.dto.response.AuthResponse;
import com.stackcoders.bookstore.entity.JwtToken;
import com.stackcoders.bookstore.entity.User;
import com.stackcoders.bookstore.exception.DuplicateResourceException;
import com.stackcoders.bookstore.exception.InvalidTokenException;
import com.stackcoders.bookstore.mapper.UserMapper;
import com.stackcoders.bookstore.repository.JwtTokenRepository;
import com.stackcoders.bookstore.repository.UserRepository;
import com.stackcoders.bookstore.repository.AdminRepository;
import com.stackcoders.bookstore.security.jwt.JwtService;
import com.stackcoders.bookstore.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final JwtTokenRepository jwtTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("An account with this email already exists");
        }

        Role assignedRole = Role.USER;
        if (request.getRole() != null && !request.getRole().isEmpty()) {
            try {
                assignedRole = Role.valueOf(request.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Ignore invalid roles and default to ordinary USER
            }
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(assignedRole)
                .build();

        userRepository.save(user);
        log.info("New user registered: {}", user.getEmail());
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidTokenException("User not found"));

        String jwt = jwtService.generateToken(user);
        persistToken(user.getUserId(), jwt);

        log.info("User logged in: {}", user.getEmail());

        return AuthResponse.builder()
                .token(jwt)
                .user(userMapper.toAuthUserSummary(user))
                .build();
    }

    @Override
    @Transactional
    public AuthResponse adminLogin(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        com.stackcoders.bookstore.entity.Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidTokenException("Admin not found"));

        if (admin.getRole() != Role.ADMIN) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Access Denied: Only Admins can login here");
        }

        String jwt = jwtService.generateToken(admin);
        persistToken(admin.getAdminId(), jwt);

        log.info("Admin logged in: {}", admin.getEmail());

        AuthResponse.AuthUserSummary summary = new AuthResponse.AuthUserSummary();
        summary.setId(admin.getAdminId());
        summary.setUsername(admin.getUsername());
        summary.setEmail(admin.getEmail());
        summary.setRole(admin.getRole().name());

        return AuthResponse.builder()
                .token(jwt)
                .user(summary)
                .build();
    }

    @Override
    @Transactional
    public void logout(String bearerToken) {
        String token = stripBearerPrefix(bearerToken);

        JwtToken storedToken = jwtTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Token not recognized"));

        storedToken.setRevoked(true);
        jwtTokenRepository.save(storedToken);

        log.info("Token revoked for userId {}", storedToken.getUserId());
    }

    @Override
    @Transactional
    public AuthResponse refresh(String bearerToken) {
        String oldToken = stripBearerPrefix(bearerToken);

        JwtToken storedToken = jwtTokenRepository.findByToken(oldToken)
                .orElseThrow(() -> new InvalidTokenException("Token not recognized"));

        if (storedToken.isRevoked()) {
            throw new InvalidTokenException("Token has already been revoked");
        }

        String username = jwtService.extractUsername(oldToken);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new InvalidTokenException("User not found"));

        // Revoke the old token before issuing a new one.
        storedToken.setRevoked(true);
        jwtTokenRepository.save(storedToken);

        String newJwt = jwtService.generateToken(user);
        persistToken(user.getUserId(), newJwt);

        log.info("Token refreshed for userId {}", user.getUserId());

        return AuthResponse.builder()
                .token(newJwt)
                .user(userMapper.toAuthUserSummary(user))
                .build();
    }

    private void persistToken(Long userId, String jwt) {
        LocalDateTime expiresAt = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis() + jwtService.getAccessTokenExpirationMs()),
                ZoneId.systemDefault());

        JwtToken tokenEntity = JwtToken.builder()
                .userId(userId)
                .token(jwt)
                .isExpired(false)
                .isRevoked(false)
                .expiresAt(expiresAt)
                .build();

        jwtTokenRepository.save(tokenEntity);
    }

    private String stripBearerPrefix(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith(SecurityConstants.BEARER_PREFIX)) {
            throw new InvalidTokenException("Missing or malformed Authorization header");
        }
        return bearerToken.substring(SecurityConstants.BEARER_PREFIX.length());
    }
}
