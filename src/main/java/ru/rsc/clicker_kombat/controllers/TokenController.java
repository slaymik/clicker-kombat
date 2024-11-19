package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.rsc.clicker_kombat.model.domain.Player;
import ru.rsc.clicker_kombat.model.domain.RefreshToken;
import ru.rsc.clicker_kombat.model.requests.TokenRefreshRequest;
import ru.rsc.clicker_kombat.model.responses.JwtResponse;
import ru.rsc.clicker_kombat.services.PlayerService;
import ru.rsc.clicker_kombat.services.RefreshTokenService;
import ru.rsc.clicker_kombat.services.JwtService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final JwtEncoder encoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final PlayerService playerService;

    @PostMapping("/token")
    public ResponseEntity<?> getToken(Authentication authentication) {
        Optional<Player> player = playerService.getPlayerIdByLogin(authentication.getName());
        if(player.isEmpty()){
            return ResponseEntity.badRequest().body("Пользователь с таким логином не найден");
        }
        UUID playerId = player.get().getId();
        JwtResponse response = new JwtResponse(encoder.encode(JwtEncoderParameters.from(jwtService.getClaims(authentication.getName()))).getTokenValue(),
                refreshTokenService.createRefreshToken(authentication.getName()).getToken().toString(),playerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(UUID.fromString(requestRefreshToken));
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body("Token is null");
        }
        if (refreshTokenService.verifyExpiration(refreshToken)) {
            JwtResponse response = new JwtResponse(encoder.encode(JwtEncoderParameters.from(jwtService.getClaims(refreshToken.getUsername()))).getTokenValue(),
                    refreshTokenService.updateRefreshToken(refreshToken).getToken().toString(), null);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(498)).body("Please send new login request");
    }
}