package ru.rsc.clicker_kombat.model.responses;

import lombok.Value;

@Value
public class ActionResult {
    Boolean isSuccess;
    String message;
}