package ru.rsc.clicker_kombat.model.responses;

import lombok.Value;

@Value
public class JwtResponse {
    String token;
    String refreshToken;
}