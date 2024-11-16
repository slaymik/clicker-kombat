package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.requests.UserRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.PlayerService;


@RestController
@RequestMapping("player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<EntityResponse> getPlayer(@RequestParam("id") String id) {
        return ResponseEntity.ok(playerService.getUser(Long.parseLong(id)));
    }

    @PostMapping("/auth")
    public ResponseEntity<EntityResponse> createPlayer(@RequestBody UserRequest response) {
        return ResponseEntity.ok(playerService.createUser(response));
    }
}