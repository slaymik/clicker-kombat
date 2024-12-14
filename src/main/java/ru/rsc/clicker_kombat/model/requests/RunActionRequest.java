package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;

import java.util.UUID;

@Value
public class RunActionRequest {
    Long runId;
    UUID playerId;
    Boolean finishedByVictory;
}
