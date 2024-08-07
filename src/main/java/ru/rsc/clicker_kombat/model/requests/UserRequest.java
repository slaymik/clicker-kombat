package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;
import ru.rsc.clicker_kombat.model.domain.Character;

import java.time.Instant;
import java.util.List;

@Value
public class UserRequest {
    Long id;
    String username;
    String token;
    Instant registrationDate;
    Instant lastOnline;
    Boolean isActive;
    List<Character> characters;
}