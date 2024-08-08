package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.requests.CharacterXpRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.CharacterXpService;

@RestController
@RequestMapping("character/xp")
@RequiredArgsConstructor
public class CharacterXpController {
    private final CharacterXpService characterXpService;

    @GetMapping
    public ResponseEntity<EntityResponse> getCharacterXp(@RequestParam("id") String id){
        return ResponseEntity.ok(characterXpService.getCharacterXp(Long.parseLong(id)));
    }

    @GetMapping("/update")
    public ResponseEntity<EntityResponse> updateCharacterXp(@RequestBody CharacterXpRequest request){
        return ResponseEntity.ok(characterXpService.updateCharacterXp(request));
    }

    @GetMapping("/add")
    public ResponseEntity<EntityResponse> addXp(@RequestBody CharacterXpRequest request){
        return ResponseEntity.ok(characterXpService.addXp(request));
    }
}