package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.requests.CharacterRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.CharacterService;

import java.util.UUID;

@RestController
@RequestMapping("character")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<EntityResponse> getAllCharacters(@RequestParam("tg_id") String id) {
        return ResponseEntity.ok(characterService.getAllCharactersByUserId(UUID.fromString(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<EntityResponse> createCharacter(@RequestBody CharacterRequest request) {
            return ResponseEntity.ok(characterService.createCharacter(request));
    }

    @GetMapping("/get")
    public ResponseEntity<EntityResponse> getCharacterById(@RequestParam("id") String id) {
        return ResponseEntity.ok(characterService.getCharacterResponse(Long.parseLong(id)));
    }

    @PostMapping("/update")
    public ResponseEntity<EntityResponse> updateCharacter(@RequestBody CharacterRequest request) {
            return ResponseEntity.ok(characterService.updateCharacter(request));
    }
}
