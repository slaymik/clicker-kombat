package ru.rsc.clicker_kombat.consts;

import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;

import java.util.UUID;

public class EntityResponseFactory {
    public static final String SUCCESS = "Success";
    public static final String ERROR = "Error";
    public static final String CHARACTER_NOT_FOUND = "Персонаж с id:%d не найден";
    public static final String USER_NOT_FOUND = "Пользователь с id:%s не найден";
    public static final String FACTION_NOT_FOUND = "Фракция с id:%d не найдена";
    public static final String RUN_NOT_FOUND = "Забег с id:%d не найдена";
    public static final String RUN_OF_OTHER_PLAYER = "Забег с id:%s принадлежит другому пользователю";
    public static final String RUN_ALREADY_FINISHED = "Забег с id:%s уже завершен";

    public static EntityResponse getCharacterNotFoundResponse(Integer id) {
        return EntityResponse.builder()
                .status(ERROR)
                .message(CHARACTER_NOT_FOUND.formatted(id))
                .build();
    }

    public static EntityResponse getUserNotFoundResponse(UUID id) {
        return EntityResponse.builder()
                .status(ERROR)
                .message(USER_NOT_FOUND.formatted(id))
                .build();
    }

    public static EntityResponse getEntityResponseSuccess(Object entity) {
        return EntityResponse.builder()
                .status(SUCCESS)
                .entity(entity)
                .build();
    }

    public static EntityResponse getEntityResponseCustomError(String message){
        return EntityResponse.builder()
                .status(ERROR)
                .message(message)
                .build();
    }

    public static EntityResponse getEntityResponseErrorFaction(Long id) {
        return EntityResponse.builder()
                .status(ERROR)
                .message(FACTION_NOT_FOUND.formatted(id))
                .build();
    }

    public static EntityResponse getRunNotFoundResponse(Long id) {
        return EntityResponse.builder()
                .status(ERROR)
                .message(RUN_NOT_FOUND.formatted(id))
                .build();
    }

    public static ActionResult getRunNotFoundActionResult(Long id) {
        return new ActionResult(false, RUN_NOT_FOUND.formatted(id));
    }

    public static EntityResponse getRunOfOtherPlayerResponse(Long id) {
        return EntityResponse.builder()
                .status(ERROR)
                .message(RUN_OF_OTHER_PLAYER.formatted(id))
                .build();
    }

    public static ActionResult getRunOfOtherPlayerActionResult(Long id) {
        return new ActionResult(false, RUN_OF_OTHER_PLAYER.formatted(id));
    }

    public static EntityResponse getRunAlreadyFinishedResponse(Long id) {
        return EntityResponse.builder()
                .status(ERROR)
                .message(RUN_ALREADY_FINISHED.formatted(id))
                .build();
    }

    public static ActionResult getRunAlreadyFinishedActionResult(Long id) {
        return new ActionResult(false, RUN_ALREADY_FINISHED.formatted(id));
    }
}