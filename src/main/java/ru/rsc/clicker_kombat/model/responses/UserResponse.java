package ru.rsc.clicker_kombat.model.responses;

import lombok.Builder;
import lombok.Data;
import ru.rsc.clicker_kombat.model.domain.Character;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String token;
    private Instant registrationDate;
    private Instant lastOnline;
    private Boolean isActive;
    private List<Character> characters;
}