package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.Character;
import ru.rsc.clicker_kombat.model.domain.Player;
import ru.rsc.clicker_kombat.model.requests.CharacterRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.rsc.clicker_kombat.consts.EntityResponseFactory.*;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    public EntityResponse createCharacter(CharacterRequest request) {
        Optional<Player> user = playerRepository.findById(request.getPlayerId());
        if (user.isPresent()) {
            Character character = Character.builder()
                    .name(request.getName())
                    .player(user.get())
                    .build();
            characterRepository.save(character);
            return getEntityResponseSuccess(character);
        } else
            return getUserNotFoundResponse(request.getPlayerId());
    }

    public EntityResponse getAllCharactersByUserId(UUID id) {
        if (playerService.getPlayer(id).getStatus().equals(SUCCESS)) {
            List<Character> characterList = characterRepository.getCharactersByPlayer_Id(id);
            return getEntityResponseSuccess(characterList);
        } else {
            return playerService.getPlayer(id);
        }
    }

    public EntityResponse getCharacterResponse(Long id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isPresent())
            return getEntityResponseSuccess(character.get());
        else
            return getCharacterNotFoundResponse(id);
    }


    public ActionResult deleteCharacter(Long id) {
        if (characterRepository.findById(id).isEmpty()) {
            return new ActionResult(false, CHARACTER_NOT_FOUND.formatted(id));
        }
        characterRepository.deleteById(id);
        if (characterRepository.findById(id).isPresent()) {
            return new ActionResult(true, "Пользователь с id: %s удален".formatted(id));
        }
        return new ActionResult(false, "Пиздец произошел");
    }

    public EntityResponse updateCharacter(CharacterRequest request) {
        Optional<Character> existingCharacter = characterRepository.findById(request.getId());
        if (existingCharacter.isPresent()) {
            Character updatedCharacter = Character.builder()
                    .name(request.getName() == null ? existingCharacter.get().getName() : request.getName())
                    .playerId(existingCharacter.get().getPlayerId())
                    .player(existingCharacter.get().getPlayer())
                    .build();
            characterRepository.save(updatedCharacter);
            return getEntityResponseSuccess(updatedCharacter);
        } else
            return getCharacterNotFoundResponse(request.getId());
    }
}