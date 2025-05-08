package ru.rsc.clicker_kombat.model.responses;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class LeaderboardResponse {
    Long runId;
    String username;
    UUID playerId;
    Integer characterGameId;
    Integer level;
    Integer duration;
    List<Integer> boostIds;
}