package ru.rsc.clicker_kombat.model.requests;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import java.util.UUID;

@Value
public class RunStateRequest {
    Long runId;
    UUID playerId;
    Integer characterId;
    Integer characterGameId;
    JsonNode characterParams;
    Integer level;
    Integer stage;
    Integer shitCoins;
    Integer enemy;
    Integer world;
    JsonNode boosts;
    JsonNode consumables;
    Integer duration;
    Boolean isHeroic;
}