package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;

@Value
public class AdminRequest {
    String username;
    String password;
}