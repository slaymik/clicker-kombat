package ru.rsc.clicker_kombat.model.responses;

import lombok.Value;

import java.util.UUID;

@Value
public class JwtResponse {
    String token;
    String refreshToken;
    UUID playerId;
}