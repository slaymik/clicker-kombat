package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;
import ru.rsc.clicker_kombat.model.domain.Character;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
public class PlayerRequest {
    UUID id;
    Instant lastOnline;
    Long upCoins;
    List<Character> characters;
}