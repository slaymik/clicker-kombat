package ru.rsc.clicker_kombat.model.responses;

import lombok.Builder;
import lombok.Value;
import ru.rsc.clicker_kombat.model.domain.Character;

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
    Integer playerLevel;
    List<Character> characters;
}
