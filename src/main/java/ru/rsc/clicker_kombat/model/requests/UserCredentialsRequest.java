package ru.rsc.clicker_kombat.model.requests;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import java.util.List;

@Value
public class UserCredentialsRequest {
    String username;
    String password;
    String playerName;
    List<Character> characters;

    public record Character(Integer characterId, String name, JsonNode items) {
    }
}