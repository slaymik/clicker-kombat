package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;
import ru.rsc.clicker_kombat.model.domain.Upgrades;

@Value
public class UpgradesRequest {
    Long characterId;
    Long upCoins;
    Upgrades.Upgrade upgrade;
}