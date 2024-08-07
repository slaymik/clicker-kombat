package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;
import ru.rsc.clicker_kombat.model.domain.Upgrades;


@Value
public class CharacterRequest {
    Long id;
    String name;
    String faction;
    Short level;
    Long upCoins;
    Long currentXp;
    Long allXp;
    Long needXp;
    Long userId;
    Upgrades upgrades;
}