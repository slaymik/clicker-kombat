package ru.rsc.clicker_kombat.model.responses;

import lombok.Value;

@Value
public class LeaderboardResponse {
    Long characterId;
    String characterName;
    Integer profit;
    Integer peakLevel;
}