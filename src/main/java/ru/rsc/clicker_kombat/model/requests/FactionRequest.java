package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;

@Value
public class FactionRequest {
    String name;
    String description;
    Boolean isAttacker;
}