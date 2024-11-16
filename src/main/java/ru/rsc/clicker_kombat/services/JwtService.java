package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JdbcUserDetailsManager userDetailsManager;

    @Value("${jwt.token.expiry}")
    private Long tokenExpiry;

    public JwtClaimsSet getClaims(String username) {
        Instant now = Instant.now();
        String scope = userDetailsManager.loadUserByUsername(username).getAuthorities().stream().findFirst().orElseThrow().getAuthority();
        return JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(tokenExpiry))
                .subject(username)
                .claim("scope", scope)
                .build();
    }
}