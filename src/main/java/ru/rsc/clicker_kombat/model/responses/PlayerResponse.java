package ru.rsc.clicker_kombat.model.responses;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class PlayerResponse {
    UUID playerId;
    String username;
    Integer rating;
    Long rank;
    Long upCoins;
    List<Character> characters;
    Integer playerLevel;
}
