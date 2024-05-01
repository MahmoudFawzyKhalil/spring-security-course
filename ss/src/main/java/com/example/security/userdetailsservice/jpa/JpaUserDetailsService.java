package com.example.security.userdetailsservice.jpa;

import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
    }

    public UserDetails loadByApiKey(String apiKey) {
        return userRepository.findByApiKey(apiKey)
                .map(SecurityUser::new)
                .orElseThrow(() -> new BadCredentialsException("API Key not found " + apiKey));
    }
}
