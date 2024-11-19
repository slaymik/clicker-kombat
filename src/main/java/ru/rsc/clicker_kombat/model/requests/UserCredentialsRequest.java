package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;

@Value
public class UserCredentialsRequest {
    String username;
    String password;
    String playerName;
}