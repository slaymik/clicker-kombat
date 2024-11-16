package ru.rsc.clicker_kombat.model.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class TokenRefreshRequest {
    private final String refreshToken;

    @JsonCreator
    public TokenRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}