package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.RefreshToken;
import ru.rsc.clicker_kombat.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token.expiry}")
    private Long expiry;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID())
                .username(username)
                .expiryDate(Instant.now().plusSeconds(expiry))
                .build();
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public boolean verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            log.warn("Refresh token was expired. Please make a new signin request");
            return false;
        }
        return true;
    }

    public RefreshToken updateRefreshToken(RefreshToken token){
        token.setExpiryDate(Instant.now().plusSeconds(expiry));
        refreshTokenRepository.save(token);
        return token;
    }

    public Optional<RefreshToken> findByToken(UUID token) {
        return refreshTokenRepository.findById(token);
    }
}