package ru.rsc.clicker_kombat.consts;

import ru.rsc.clicker_kombat.model.responses.EntityResponse;

public class EntityResponseConstsAndFactory {
    public static final String SUCCESS = "Success";
    public static final String ERROR = "Error";
    public static final String CHARACTER_NOT_FOUND = "Персонаж с id:%d не найден";
    public static final String USER_NOT_FOUND = "Пользователь с id:%d не найден";
    public static final String FACTION_NOT_FOUND = "Фракция с id:%d не найдена";

    public static EntityResponse getEntityResponseErrorCharacter(Long id) {
        return EntityResponse.builder()
                .status(ERROR)
                .message(CHARACTER_NOT_FOUND.formatted(id))
                .build();
    }

    public static EntityResponse getEntityResponseErrorUser(Long id){
        return EntityResponse.builder()
                .status(ERROR)
                .message(USER_NOT_FOUND.formatted(id))
                .build();
    }

    public static EntityResponse getEntityResponseSuccess(Object entity){
        return EntityResponse.builder()
                .status(SUCCESS)
                .entity(entity)
                .build();
    }

    public static EntityResponse getEntityResponseErrorFaction(Long id){
        return EntityResponse.builder()
                .status(ERROR)
                .message(FACTION_NOT_FOUND.formatted(id))
                .build();
    }
}