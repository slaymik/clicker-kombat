package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rsc.clicker_kombat.model.domain.Player;
import ru.rsc.clicker_kombat.model.domain.UserCredentials;
import ru.rsc.clicker_kombat.model.requests.UserCredentialsRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.services.CharacterService;
import ru.rsc.clicker_kombat.services.PlayerService;
import ru.rsc.clicker_kombat.services.UserService;
import ru.rsc.clicker_kombat.utils.Roles;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final PlayerService playerService;
    private final CharacterService characterService;


    @PostMapping("/create")
    public ResponseEntity<ActionResult> createUser(@RequestBody UserCredentialsRequest request) {
        log.info("Запрос на регистацию пользователя с username: {}", request.getUsername());

        UserCredentials userCredentials = UserCredentials.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(Roles.PLAYER)
                .build();
        ActionResult actionResult = userService.registerUser(userCredentials);

        if (actionResult.getIsSuccess()) {
            log.info(actionResult.getMessage());
            Player player = playerService.createUser(request.getUsername(), request.getPlayerName());
            characterService.createAllCharactersForPlayer(player.getId(), request.getCharacters());
        } else {
            log.error(actionResult.getMessage());
        }

        return ResponseEntity.ok(actionResult);
    }

    @PostMapping("/create-characters")
    public ResponseEntity<ActionResult> createCharacters(@RequestBody CreateCharactersRequest request) {
        characterService.createAllCharactersForPlayer(UUID.fromString(request.playerId()), request.characters());
        return ResponseEntity.ok(new ActionResult(true, "Все заебись!"));
    }

    public record CreateCharactersRequest(String playerId, List<UserCredentialsRequest.Character> characters) {

    }
}