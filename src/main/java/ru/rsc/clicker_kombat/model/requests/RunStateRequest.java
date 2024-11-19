package ru.rsc.clicker_kombat.model.requests;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class RunStateRequest {
    UUID player_id;
    JsonNode character;
    Integer level;
    Long shitCoins;
    Long runUpCoins;
    Integer enemy;
    Integer world;
    JsonNode boosts;
    JsonNode consumables;
    Instant startTime;
    Instant endTime;
    Integer duration;
    Boolean isEnded;
}