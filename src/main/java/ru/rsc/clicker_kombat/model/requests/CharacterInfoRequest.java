package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;
import ru.rsc.clicker_kombat.model.domain.Character;
import ru.rsc.clicker_kombat.model.domain.Faction;

@Value
public class CharacterInfoRequest {
    Character character;
    Faction faction;
    Integer profit;
}