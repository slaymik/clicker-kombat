package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.consts.XPConsts;
import ru.rsc.clicker_kombat.model.domain.Character;
import ru.rsc.clicker_kombat.model.domain.CharacterXp;
import ru.rsc.clicker_kombat.model.requests.CharacterXpRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.CharacterXpRepository;
import ru.rsc.clicker_kombat.utils.calcs.XpCalc;

import java.util.Optional;

import static ru.rsc.clicker_kombat.consts.EntityResponseFactory.*;

@Service
@RequiredArgsConstructor
public class CharacterXpService {
    private final CharacterXpRepository characterXpRepository;

    public EntityResponse getCharacterXp(Integer characterId) {
        Optional<CharacterXp> xp = characterXpRepository.findCharacterXpByCharacter_Id(characterId);
        if (xp.isPresent())
            return getEntityResponseSuccess(xp.get());
        else
            return getCharacterNotFoundResponse(characterId);
    }

    public EntityResponse updateCharacterXp(CharacterXpRequest request) {
        Optional<CharacterXp> existingXp = characterXpRepository.findCharacterXpByCharacter_Id(request.getCharacterId());
        if (existingXp.isPresent()) {
            Short newLevel = request.getLevel();
            Integer newXpRequirement = XpCalc.calcXpRequired(newLevel);
            CharacterXp updatedXp = CharacterXp.builder()
                    .character(existingXp.get().getCharacter())
                    .level(newLevel == null ? existingXp.get().getLevel() : request.getLevel())
                    .allXp(newLevel == null ? existingXp.get().getAllXp() : XpCalc.getAllXp(existingXp.get().getCurrentXp(),
                            existingXp.get().getAllXp(), existingXp.get().getLevel(), newLevel))
                    .xpRequirement(newLevel == null ? existingXp.get().getXpRequirement() : newXpRequirement)
                    .needXp(newLevel == null ? existingXp.get().getNeedXp() : newXpRequirement - existingXp.get().getCurrentXp())
                    .build();
            characterXpRepository.save(updatedXp);
            return getEntityResponseSuccess(updatedXp);
        } else
            return getCharacterNotFoundResponse(request.getCharacterId());
    }

    public void createCharacterXp(Character character) {
        CharacterXp characterXp = CharacterXp.builder()
                .character(character)
                .currentXp(XPConsts.BASE_XP)
                .allXp(XPConsts.BASE_XP)
                .level((short) 1)
                .xpRequirement(XpCalc.calcXpRequired((short) 1))
                .needXp(XpCalc.calcXpRequired((short) 1))
                .build();
        characterXpRepository.save(characterXp);
    }

    public EntityResponse addXp(Integer characterId, Integer addedXp) {
        Optional<CharacterXp> xp = characterXpRepository.findById(characterId);
        if (xp.isPresent()) {
            int newCurrentXp = xp.get().getCurrentXp() + addedXp;
            Integer xpRequirement = xp.get().getXpRequirement();
            Short level = xp.get().getLevel();
            while (newCurrentXp >= xpRequirement) {
                newCurrentXp -= xpRequirement;
                level++;
                xpRequirement = XpCalc.calcXpRequired(level);
            }
            CharacterXp characterXp = CharacterXp.builder()
                    .character(xp.get().getCharacter())
                    .level(level)
                    .currentXp(newCurrentXp)
                    .allXp(xp.get().getAllXp() + addedXp)
                    .xpRequirement(xpRequirement)
                    .needXp(xpRequirement-newCurrentXp)
                    .build();
            characterXpRepository.save(characterXp);
            return getEntityResponseSuccess(characterXp);
        } else
            return getCharacterNotFoundResponse(characterId);
    }
}