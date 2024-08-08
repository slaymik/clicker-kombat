package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.requests.UpgradesRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.UpgradesService;

@RestController
@RequestMapping("character/upgrades")
@RequiredArgsConstructor
public class UpgradesController {
    private final UpgradesService upgradesService;

    @GetMapping
    public ResponseEntity<EntityResponse> getUpgrades(@RequestParam("id") String id){
        return ResponseEntity.ok(upgradesService.getUpgrades(Long.parseLong(id)));
    }

    @PostMapping("/add_coins")
    public ResponseEntity<EntityResponse> addUpCoins(@RequestBody UpgradesRequest request){
        return ResponseEntity.ok(upgradesService.addUpCoins(request));
    }

    @GetMapping("/buy")
    public ResponseEntity<EntityResponse> buyUpgrades(@RequestBody UpgradesRequest request){
        return ResponseEntity.ok(upgradesService.buyUpgrade(request));
    }
}