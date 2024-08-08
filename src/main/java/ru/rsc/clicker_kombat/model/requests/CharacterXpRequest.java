package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;

@Value
public class CharacterXpRequest {
    Long characterId;
    Short level;
    Long addedXp;
    Long allXp;
    Long xpRequirement;
    Long needXp;
}