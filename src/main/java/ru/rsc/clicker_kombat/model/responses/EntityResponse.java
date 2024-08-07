package ru.rsc.clicker_kombat.model.responses;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EntityResponse {
    String status;
    Object entity;
    String message;
}