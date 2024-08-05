package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.requests.CharacterRequest;
import ru.rsc.clicker_kombat.services.CharacterService;
import ru.rsc.clicker_kombat.services.UserService;

import java.util.Map;

@RestController
@RequestMapping("character")
@RequiredArgsConstructor
public class CharacterController {
    private final UserService userService;
    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCharacters(@RequestParam("tg_id") String id) {
        return ResponseEntity.ok(characterService.getAllCharactersByUserId(Long.parseLong(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createCharacter(@RequestBody CharacterRequest request) {
        if (userService.getUserOrError(request.getId()).containsKey("User"))
            return ResponseEntity.ok(characterService.createCharacter(request));
        else
            return ResponseEntity.ok(userService.getUserOrError(request.getId()));
    }

    @GetMapping("/get/character")
    public ResponseEntity<Map<String, Object>> getCharacterById(@RequestParam("id") String id) {
        return ResponseEntity.ok(characterService.getCharacterOrError(Long.parseLong(id)));
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateCharacter(@RequestBody CharacterRequest request) {
        if (characterService.getCharacterOrError(request.getId()).containsKey("Character"))
            return ResponseEntity.ok(characterService.updateCharacter(request));
        else
            return ResponseEntity.ok(characterService.getCharacterOrError(request.getId()));
    }
}
