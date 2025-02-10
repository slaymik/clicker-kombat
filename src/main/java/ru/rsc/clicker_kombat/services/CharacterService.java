package ru.rsc.clicker_kombat.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.rsc.clicker_kombat.model.domain.Character;
import ru.rsc.clicker_kombat.model.domain.Player;
import ru.rsc.clicker_kombat.model.requests.BuyItemRequest;
import ru.rsc.clicker_kombat.model.requests.CharacterRequest;
import ru.rsc.clicker_kombat.model.requests.UserCredentialsRequest;
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
    private final CharacterXpService characterXpService;
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    public EntityResponse createCharacter(UUID playerId, String characterName) {
        Optional<Player> user = playerRepository.findById(playerId);
        if (user.isPresent()) {
            Character character = Character.builder()
                    .name(characterName)
                    .player(user.get())
                    .build();
            characterRepository.save(character);
            return getEntityResponseSuccess(character);
        } else
            return getUserNotFoundResponse(playerId);
    }

    public EntityResponse getAllCharactersByUserId(UUID id) {
        if (playerService.getPlayer(id).getStatus().equals(SUCCESS)) {
            List<Character> characterList = characterRepository.getCharactersByPlayer_Id(id);
            return getEntityResponseSuccess(characterList);
        } else {
            return getUserNotFoundResponse(id);
        }
    }

    public EntityResponse getCharacterResponse(Integer id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isPresent())
            return getEntityResponseSuccess(character.get());
        else
            return getCharacterNotFoundResponse(id);
    }


    public ActionResult deleteCharacter(Integer id) {
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
                    .player(existingCharacter.get().getPlayer())
                    .build();
            characterRepository.save(updatedCharacter);
            return getEntityResponseSuccess(updatedCharacter);
        } else
            return getCharacterNotFoundResponse(request.getId());
    }

    public void createAllCharactersForPlayer(UUID playerId, List<UserCredentialsRequest.Character> characters) {
        Optional<Player> user = playerRepository.findById(playerId);
        if (user.isPresent()) {
            for (UserCredentialsRequest.Character characterRequest : characters) {
                Character character = Character.builder()
                        .player(user.get())
                        .characterGameId(characterRequest.characterId())
                        .name(characterRequest.name())
                        .items(characterRequest.items())
                        .build();
                characterRepository.save(character);
                characterXpService.createCharacterXp(character);
            }
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ActionResult buyItem(BuyItemRequest request) {
        Optional<Character> characterOptional = characterRepository.findById(request.getCharacterId());
        if (characterOptional.isEmpty()) {
            return new ActionResult(false, "Персонаж не найден");
        }

        Character character = characterOptional.get();
        Optional<Player> playerOptional = playerRepository.findById(characterOptional.get().getPlayer().getId());
        if (playerOptional.isEmpty()) {
            return new ActionResult(false, "Пользователь не найден");
        }

        Player player = playerOptional.get();
        if (player.getUpCoins() < request.getPrice()) {
            return new ActionResult(false, "Недостаточно up coins");
        }

        player.setUpCoins(player.getUpCoins() - request.getPrice());
        ArrayNode items = (ArrayNode) character.getItems();
        for (JsonNode item : items) {
            if (item.get("itemId").asInt() == request.getItemId()) {
                ObjectNode itemNode = (ObjectNode) item;
                IntNode level = new IntNode(itemNode.findValue("level").asInt() + 1);
                itemNode.set("level", level);
                characterRepository.save(character);
                break;
            }
        }
        return new ActionResult(true, "Успех!");
    }
}