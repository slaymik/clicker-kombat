package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.domain.Run;
import ru.rsc.clicker_kombat.model.requests.RunStateRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.PlayerService;
import ru.rsc.clicker_kombat.services.RunService;

import java.util.UUID;

import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.getEntityResponseSuccess;

@RestController
@RequestMapping("/run")
@RequiredArgsConstructor
public class RunController {
    private final RunService runService;
    private final PlayerService playerService;

    @PostMapping("/save")
    public ResponseEntity<EntityResponse> saveRun(@RequestBody RunStateRequest request) {
        Run run = runService.saveRun(request);
        if (run.getIsEnded()){
            playerService.updateRating(run.getPlayerId());
        }
        return ResponseEntity.ok(getEntityResponseSuccess(run));
    }

    @GetMapping("/get-last")
    public ResponseEntity<EntityResponse> getLastRun(@RequestParam("player-id") UUID id) {
        EntityResponse response = runService.getLastRunByPlayerId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<EntityResponse> getAllRunsByPlayerId(@RequestParam("player-id") UUID id,
                                                               @RequestParam("page") Integer page, @RequestParam("size") Integer size){
        EntityResponse response = runService.getAllRunsByPlayerId(id, page, size);
        return ResponseEntity.ok(response);
    }
}