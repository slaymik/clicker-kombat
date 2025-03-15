package ru.rsc.clicker_kombat.model.requests;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import java.util.List;

@Value
public class CreateCharactersRequest {
    String playerId;
    List<Character> characters;

    public record Character(Integer characterGameId, JsonNode items) {
    }
}
