package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;

import java.util.UUID;

@Value
public class CharacterRequest {
    Integer id;
    String name;
    UUID playerId;
    Long factionId;
}