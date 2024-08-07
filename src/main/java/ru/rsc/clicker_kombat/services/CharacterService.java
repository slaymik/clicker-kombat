package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.consts.XPConsts;
import ru.rsc.clicker_kombat.model.domain.Character;
import ru.rsc.clicker_kombat.model.domain.CharacterInfo;
import ru.rsc.clicker_kombat.model.domain.CharacterXp;
import ru.rsc.clicker_kombat.model.domain.Faction;
import ru.rsc.clicker_kombat.model.requests.CharacterRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.*;
import ru.rsc.clicker_kombat.utils.calcs.XpCalc;

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
    private final FactionRepository factionRepository;
    private final CharacterInfoRepository characterInfoRepository;
    private final CharacterXpRepository characterXpRepository;

    public EntityResponse createCharacter(CharacterRequest request) {
        Faction faction = factionRepository.findById(request.getFactionId()).orElseThrow();

        Character character = Character.builder()
                .name(request.getName())
                .user(userRepository.getReferenceById(request.getId()))
                .level((short) 1)
                .build();
        characterRepository.save(character);

        CharacterInfo characterInfo = CharacterInfo.builder()
                .characterId(character.getId())
                .factionId(faction.getId())
                .factionName(faction.getName())
                .faction(faction)
                .profit(0)
                .build();
        characterInfoRepository.save(characterInfo);

        CharacterXp characterXp = CharacterXp.builder()
                .characterId(character.getId())
                .currentXp(XPConsts.BASE_XP.longValue())
                .allXp(XPConsts.BASE_XP.longValue())
                .xpRequirement(XPConsts.BASE_NEED_XP.longValue())
                .needXp(XPConsts.BASE_NEED_XP.longValue())
                .build();
        characterXpRepository.save(characterXp);

        return EntityResponse.builder()
                .status(SUCCESS)
                .entity(character)
                .build();
    }

    public EntityResponse getAllCharactersByUserId(Long id) {
        if (userService.getUser(id).getStatus().equals(SUCCESS)) {
            List<Character> characterList = characterRepository.getCharactersByUser_Id(id);
            return EntityResponse.builder()
                    .status(SUCCESS)
                    .entity(characterList)
                    .build();
        } else {
            return userService.getUser(id);
        }
    }

    public EntityResponse getCharacter(Long id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isPresent())
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
        if (characterRepository.findById(id).isEmpty()) {
            return new ActionResult(false, "Пользователь с id: %s не найден".formatted(id));
        }
        characterRepository.deleteById(id);
        if (characterRepository.findById(id).isPresent()) {
            return new ActionResult(true, "Пользователь с id: %s удален".formatted(id));
        }
        return new ActionResult(false, "Пиздец произошел");
    }

    public EntityResponse updateCharacter(CharacterRequest request) {
        Character existingCharacter = characterRepository.getReferenceById(request.getId());
        Character updatedCharacter = Character.builder()
                .name(request.getName() == null ? existingCharacter.getName() : request.getName())
                .level(request.getLevel() == null ? existingCharacter.getLevel() : request.getLevel())
                .userId(existingCharacter.getUserId())
                .user(existingCharacter.getUser())
                .build();
        characterRepository.save(updatedCharacter);

        CharacterXp existingCharacterXp = characterXpRepository.getReferenceById(request.getId());
        CharacterXp updatedCharacterXp = CharacterXp.builder()
                .characterId(updatedCharacter.getId())
                .character(updatedCharacter)
                .currentXp(request.getCurrentXp() == null? existingCharacterXp.getCurrentXp() : request.getCurrentXp())
                .allXp(request.getLevel() == null? existingCharacterXp.getAllXp() : XpCalc.getAllXp(existingCharacterXp.getCurrentXp(),
                        existingCharacterXp.getAllXp(), existingCharacter.getLevel(), updatedCharacter.getLevel()))
                .build();
        characterXpRepository.save(updatedCharacterXp);

        return EntityResponse.builder()
                .status(SUCCESS)
                .entity(updatedCharacter)
                .build();
    }
}