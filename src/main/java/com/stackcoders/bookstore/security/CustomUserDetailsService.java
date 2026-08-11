package com.stackcoders.bookstore.security;

import com.stackcoders.bookstore.repository.UserRepository;
import com.stackcoders.bookstore.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<com.stackcoders.bookstore.entity.User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }
        Optional<com.stackcoders.bookstore.entity.Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return admin.get();
        }
        throw new UsernameNotFoundException("No user found with email: " + email);
    }
}
