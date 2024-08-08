package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.consts.XPConsts;
import ru.rsc.clicker_kombat.model.domain.CharacterXp;
import ru.rsc.clicker_kombat.model.requests.CharacterXpRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.CharacterRepository;
import ru.rsc.clicker_kombat.repository.CharacterXpRepository;
import ru.rsc.clicker_kombat.utils.calcs.XpCalc;

import java.util.Optional;

import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.*;

@Service
@RequiredArgsConstructor
public class CharacterXpService {
    private final CharacterXpRepository characterXpRepository;
    private final CharacterRepository characterRepository;

    public EntityResponse getCharacterXp(Long id) {
        Optional<CharacterXp> xp = characterXpRepository.findById(id);
        if (xp.isPresent())
            return getEntityResponseSuccess(xp.get());
        else
            return getEntityResponseErrorCharacter(id);
    }

    public EntityResponse updateCharacterXp(CharacterXpRequest request) {
        Optional<CharacterXp> existingXp = characterXpRepository.findById(request.getCharacterId());
        if (existingXp.isPresent()) {
            Short newLevel = request.getLevel();
            long newXpRequirement = (long) XPConsts.BASE_NEED_XP * XPConsts.getXpMultiplier(newLevel);
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
            return getEntityResponseErrorCharacter(request.getCharacterId());
    }

    public void createCharacterXp(Long id) {
        CharacterXp characterXp = CharacterXp.builder()
                .character(characterRepository.findById(id).orElseThrow())
                .currentXp(XPConsts.BASE_XP.longValue())
                .allXp(XPConsts.BASE_XP.longValue())
                .xpRequirement(XPConsts.BASE_NEED_XP.longValue())
                .needXp(XPConsts.BASE_NEED_XP.longValue())
                .build();
        characterXpRepository.save(characterXp);
    }

    public EntityResponse addXp(CharacterXpRequest request) {
        Optional<CharacterXp> xp = characterXpRepository.findById(request.getCharacterId());
        if (xp.isPresent()) {
            long newCurrentXp = xp.get().getCurrentXp() + request.getAddedXp();
            Long xpRequirement = xp.get().getXpRequirement();
            Short level = xp.get().getLevel();
            while (newCurrentXp >= xpRequirement) {
                newCurrentXp -= xpRequirement;
                level++;
                xpRequirement = ((long) XPConsts.getXpMultiplier(level) * XPConsts.BASE_NEED_XP);
            }
            CharacterXp characterXp = CharacterXp.builder()
                    .character(xp.get().getCharacter())
                    .level(level)
                    .currentXp(newCurrentXp)
                    .allXp(xp.get().getAllXp() + request.getAddedXp())
                    .xpRequirement(xpRequirement)
                    .needXp(xpRequirement-newCurrentXp)
                    .build();
            characterXpRepository.save(characterXp);
            return getEntityResponseSuccess(characterXp);
        } else
            return getEntityResponseErrorCharacter(request.getCharacterId());
    }
}