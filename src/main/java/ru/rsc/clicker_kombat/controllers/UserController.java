package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rsc.clicker_kombat.model.domain.UserCredentials;
import ru.rsc.clicker_kombat.model.requests.UserCredentialsRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.services.PlayerService;
import ru.rsc.clicker_kombat.services.UserService;
import ru.rsc.clicker_kombat.utils.Roles;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final PlayerService playerService;

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
            playerService.createUser(request.getUsername(), request.getPlayerName());
        } else {
            log.error(actionResult.getMessage());
        }

        return ResponseEntity.ok(actionResult);
    }
}