package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;

@Value
public class CharacterXpRequest {
    Integer characterId;
    Short level;
    Integer addedXp;
    Integer allXp;
    Integer xpRequirement;
    Integer needXp;
}