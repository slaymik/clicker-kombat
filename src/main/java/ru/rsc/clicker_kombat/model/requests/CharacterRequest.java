package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;

@Value
public class CharacterRequest {
    Long id;
    String name;
    Long userId;
    Long factionId;
}