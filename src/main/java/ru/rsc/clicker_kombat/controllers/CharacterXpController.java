package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.requests.CharacterXpRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.CharacterXpService;

@RestController
@RequestMapping("character-xp")
@RequiredArgsConstructor
public class CharacterXpController {
    private final CharacterXpService characterXpService;

    @GetMapping("/get")
    public ResponseEntity<EntityResponse> getCharacterXp(@RequestParam("characterId") Integer characterId){
        return ResponseEntity.ok(characterXpService.getCharacterXp(characterId));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityResponse> updateCharacterXp(@RequestBody CharacterXpRequest request){
        return ResponseEntity.ok(characterXpService.updateCharacterXp(request));
    }
}