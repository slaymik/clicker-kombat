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

    @GetMapping("/get")
    public ResponseEntity<EntityResponse> getPlayer(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(playerService.getUser(id));
    }

    @PostMapping("/update")
    public ResponseEntity<ActionResult> updatePlayer(@RequestBody PlayerRequest request) {
        return ResponseEntity.ok(playerService.updatePlayer(request));
    }

    @PostMapping("/start-session")
    public ResponseEntity<ActionResult> addSession(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(playerService.addSession(id));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<EntityResponse> getRankLeaderboard(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        if (size > 500) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(playerService.getRankLeaderboard(size, page));
    }
}