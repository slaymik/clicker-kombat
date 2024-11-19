package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.requests.PlayerRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.PlayerService;

import java.util.UUID;


@RestController
@RequestMapping("player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<EntityResponse> getPlayer(@RequestParam("id") String id) {
        return ResponseEntity.ok(playerService.getUser(UUID.fromString(id)));
    }

    @GetMapping("/update")
    public ResponseEntity<ActionResult> updatePlayer(@RequestBody PlayerRequest request){
        return ResponseEntity.ok(playerService.updatePlayer(request));
    }
}