package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.requests.CharacterRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.CharacterService;
import ru.rsc.clicker_kombat.services.UserService;

import static ru.rsc.clicker_kombat.consts.EntityResponseStatuses.SUCCESS;

@RestController
@RequestMapping("character")
@RequiredArgsConstructor
public class CharacterController {
    private final UserService userService;
    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<EntityResponse> getAllCharacters(@RequestParam("tg_id") String id) {
        return ResponseEntity.ok(characterService.getAllCharactersByUserId(Long.parseLong(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<EntityResponse> createCharacter(@RequestBody CharacterRequest request) {
        EntityResponse response = userService.getUser(request.getId());
        if (response.getStatus().equals(SUCCESS))
            return ResponseEntity.ok(characterService.createCharacter(request));
        else
            return ResponseEntity.ok(response);
    }

    @GetMapping("/get/character")
    public ResponseEntity<EntityResponse> getCharacterById(@RequestParam("id") String id) {
        return ResponseEntity.ok(characterService.getCharacter(Long.parseLong(id)));
    }

    @PostMapping("/update")
    public ResponseEntity<EntityResponse> updateCharacter(@RequestBody CharacterRequest request) {
        EntityResponse response = characterService.getCharacter(request.getId());
        if (response.getStatus().equals(SUCCESS))
            return ResponseEntity.ok(characterService.updateCharacter(request));
        else
            return ResponseEntity.ok(response);
    }
}
