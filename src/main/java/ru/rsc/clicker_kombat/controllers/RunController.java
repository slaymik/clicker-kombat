package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.requests.RunStateRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.RunService;

import java.util.UUID;

@RestController
@RequestMapping("/run")
@RequiredArgsConstructor
public class RunController {
    private final RunService runService;

    @PostMapping("/save")
    public ResponseEntity<EntityResponse> saveRun(@RequestBody RunStateRequest request) {
        EntityResponse response = runService.saveRun(request);
        return ResponseEntity.ok(response);
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