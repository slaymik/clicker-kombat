package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.domain.Player;
import ru.rsc.clicker_kombat.model.requests.RunActionRequest;
import ru.rsc.clicker_kombat.model.requests.RunStateRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.PlayerService;
import ru.rsc.clicker_kombat.services.RunService;

import java.util.Optional;
import java.util.UUID;

import static ru.rsc.clicker_kombat.consts.EntityResponseFactory.getUserNotFoundResponse;

@RestController
@RequestMapping("/run")
@RequiredArgsConstructor
@Slf4j
public class RunController {
    private final RunService runService;
    private final PlayerService playerService;

    @PostMapping("/start")
    public ResponseEntity<EntityResponse> startRun(@RequestBody RunStateRequest request) {
        return ResponseEntity.ok(runService.startRun(request));
    }

    @PostMapping("/save")
    public ResponseEntity<ActionResult> saveRun(@RequestBody RunStateRequest request) {
        return ResponseEntity.ok(runService.saveRun(request));
    }

    @PostMapping("/finish")
    public ResponseEntity<EntityResponse> finishRun(@RequestBody RunActionRequest request) {
        ResponseEntity<EntityResponse> response = ResponseEntity.ok(runService.finishRun(request));
        playerService.updateRating(request.getPlayerId());
        return response;
    }

    @PostMapping("/remove")
    public ResponseEntity<ActionResult> removeRun(@RequestBody RunActionRequest request) {
        return ResponseEntity.ok(runService.removeUnfinishedRun(request.getRunId(), request.getPlayerId()));
    }

    @GetMapping("/get-last")
    public ResponseEntity<EntityResponse> getLastRun(@RequestParam("player-id") UUID id) {
        EntityResponse response = runService.getLastRunByPlayerId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<EntityResponse> getAllRunsByPlayerId(@RequestParam("player-id") UUID id,
                                                               @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        EntityResponse response = runService.getAllRunsByPlayerId(id, page, size);
        return ResponseEntity.ok(response);
    }
}