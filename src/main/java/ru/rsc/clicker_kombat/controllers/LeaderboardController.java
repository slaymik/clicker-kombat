package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.RunService;

@RestController
@RequiredArgsConstructor
public class LeaderboardController {
    private final RunService runService;

    @GetMapping("/leaderboard")
    public ResponseEntity<EntityResponse> getMaxLevelLeaderboard(@RequestParam("size") Integer size, @RequestParam("page") Integer page,
                                                                 @RequestParam(name = "character-id", required = false, defaultValue = "-1") Integer characterId) {
        if (size > 500) {
            return ResponseEntity.badRequest().body(null);
        }
        if (characterId == -1) {
            return ResponseEntity.ok(runService.getMaxLevelLeaderboard(size, page));
        }
        return ResponseEntity.ok(runService.getMaxLevelLeaderboardByCharacterId(size, page, characterId));
    }
}
