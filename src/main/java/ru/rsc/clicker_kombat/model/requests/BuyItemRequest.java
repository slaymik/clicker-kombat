package ru.rsc.clicker_kombat.model.requests;

import lombok.Value;

@Value
public class BuyItemRequest {
    String playerId;
    Integer characterId;
    Integer itemId;
    Integer price;
}