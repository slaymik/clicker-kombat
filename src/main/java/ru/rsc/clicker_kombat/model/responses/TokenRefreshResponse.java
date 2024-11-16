package ru.rsc.clicker_kombat.model.responses;

import lombok.Value;

@Value
public class TokenRefreshResponse {
    String accessToken;
    String refreshToken;
}