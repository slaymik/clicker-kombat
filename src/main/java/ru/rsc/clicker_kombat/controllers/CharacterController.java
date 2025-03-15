package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.domain.Player;
import ru.rsc.clicker_kombat.model.requests.BuyItemRequest;
import ru.rsc.clicker_kombat.model.requests.CharacterRequest;
import ru.rsc.clicker_kombat.model.requests.CreateCharactersRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.CharacterService;
import ru.rsc.clicker_kombat.services.PlayerService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;
    private final PlayerService playerService;

    @GetMapping("/get-all")
    public ResponseEntity<EntityResponse> getAllCharacters(@RequestParam("playerId") String id) {
        return ResponseEntity.ok(characterService.getAllCharactersByUserId(UUID.fromString(id)));
    }

    @GetMapping("/get")
    public ResponseEntity<EntityResponse> getCharacterById(@RequestParam("characterId") String id) {
        return ResponseEntity.ok(characterService.getCharacterResponse(Integer.valueOf(id)));
    }

    @PostMapping("/update")
    public ResponseEntity<EntityResponse> updateCharacter(@RequestBody CharacterRequest request) {
        return ResponseEntity.ok(characterService.updateCharacter(request));
    }

    @PostMapping("/update-characters")
    public ResponseEntity<ActionResult> createCharacters(@RequestBody CreateCharactersRequest request) {
        characterService.createAllCharactersForPlayer(UUID.fromString(request.getPlayerId()), request.getCharacters());
        return ResponseEntity.ok(new ActionResult(true, "Все заебись!"));
    }

    @PostMapping("/buy-item")
    public ResponseEntity<ActionResult> buyItem(@RequestBody BuyItemRequest request, Authentication auth) {
        Optional<Player> playerOpt = playerService.findPlayer(UUID.fromString(request.getPlayerId()));
        if (playerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ActionResult(false, "Пользователя с таким id не существует"));
        }

        String username = playerOpt.get().getUsername();
        if (!Objects.equals(username, auth.getName())) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(characterService.buyItem(request));
    }
}
