package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.consts.XPConsts;
import ru.rsc.clicker_kombat.model.domain.Character;
import ru.rsc.clicker_kombat.model.requests.CharacterRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.repository.CharacterRepository;
import ru.rsc.clicker_kombat.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Map<String,Object> createCharacter(CharacterRequest request) {
        Map<String,Object> map = new HashMap<>();
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
        map.put("Created Character",character);
        return map;
    }

    public Map<String,Object> getAllCharactersByUserId(Long id) {
        if(userService.getUserOrError(id).containsKey("User")){
            Map<String,Object> map = new HashMap<>();
            List<Character> characterList = characterRepository.getCharactersByUser_Id(id);
            map.put("Characters", characterList);
            return map;
        }else {
            return userService.getUserOrError(id);
        }
    }

    public Map<String,Object> getCharacterOrError(Long id) {
        Map<String,Object> map = new HashMap<>();
        if(characterRepository.findById(id).isPresent())
            map.put("Character",characterRepository.findById(id));
        else
            map.put("Error","Персонаж с id:%s не найден".formatted(id));
        return map;
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

    public Map<String,Object> updateCharacter(CharacterRequest request) {
        Map<String,Object> map = new HashMap<>();
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
        map.put("Updated Character",updatedCharacter);
        return map;
    }
}