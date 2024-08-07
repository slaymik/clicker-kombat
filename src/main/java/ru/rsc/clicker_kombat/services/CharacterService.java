package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.consts.XPConsts;
import ru.rsc.clicker_kombat.model.domain.Character;
import ru.rsc.clicker_kombat.model.requests.CharacterRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.CharacterRepository;
import ru.rsc.clicker_kombat.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static ru.rsc.clicker_kombat.consts.EntityResponseStatuses.ERROR;
import static ru.rsc.clicker_kombat.consts.EntityResponseStatuses.SUCCESS;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public EntityResponse createCharacter(CharacterRequest request) {
        Character character = Character.builder()
                .name(request.getName())
                .faction(request.getFaction())
                .currentXp(XPConsts.BASE_XP.longValue())
                .allXp(XPConsts.BASE_XP.longValue())
                .needXp(XPConsts.BASE_NEED_XP.longValue())
                .user(userRepository.getReferenceById(request.getId()))
                .level((short) 1)
                .upgrades(null)
                .upCoins(0L)
                .build();
        characterRepository.save(character);
        return EntityResponse.builder()
                .status(SUCCESS)
                .build();
    }

    public EntityResponse getAllCharactersByUserId(Long id) {
        if(userService.getUser(id).getStatus().equals(SUCCESS)){
            List<Character> characterList = characterRepository.getCharactersByUser_Id(id);
            return EntityResponse.builder()
                    .status(SUCCESS)
                    .entity(characterList)
                    .build();
        }else {
            return userService.getUser(id);
        }
    }

    public EntityResponse getCharacter(Long id) {
        Optional<Character> character = characterRepository.findById(id);
        if(character.isPresent())
            return EntityResponse.builder()
                    .status(SUCCESS)
                    .entity(character.orElseThrow())
                    .build();
        else
            return EntityResponse.builder()
                    .status(ERROR)
                    .message("Персонаж с id:%s не найден".formatted(id))
                    .build();
    }

    public ActionResult deleteCharacter(Long id) {
        if(characterRepository.findById(id).isEmpty()){
            return new ActionResult(false,"Пользователь с id: %s не найден".formatted(id));
        }
        characterRepository.deleteById(id);
        if (characterRepository.findById(id).isPresent()){
            return new ActionResult(true,"Пользователь с id: %s удален".formatted(id));
        }
        return new ActionResult(false,"Пиздец произошел");
    }

    public EntityResponse updateCharacter(CharacterRequest request) {
        Character existingCharacter = characterRepository.getReferenceById(request.getId());
        Character updatedCharacter = Character.builder()
                .name(request.getName() == null ? existingCharacter.getName() : request.getName())
                .faction(request.getFaction() == null ? existingCharacter.getFaction() : request.getFaction())
                .level(request.getLevel() == null ? existingCharacter.getLevel() : request.getLevel())
                .upCoins(request.getUpCoins() == null ? existingCharacter.getUpCoins() : request.getUpCoins())
                .currentXp(request.getCurrentXp() == null ? existingCharacter.getCurrentXp() : request.getCurrentXp())
                .userId(existingCharacter.getUserId())
                .upgrades(request.getUpgrades() == null ? existingCharacter.getUpgrades() : request.getUpgrades())
                .user(existingCharacter.getUser())
                .build();
        characterRepository.save(updatedCharacter);
        return EntityResponse.builder()
                .status(SUCCESS)
                .entity(updatedCharacter)
                .build();
    }
}