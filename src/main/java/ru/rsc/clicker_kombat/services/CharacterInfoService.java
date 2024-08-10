package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.CharacterInfo;
import ru.rsc.clicker_kombat.model.requests.CharacterInfoRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.model.responses.LeaderboardResponse;
import ru.rsc.clicker_kombat.repository.CharacterInfoRepository;
import ru.rsc.clicker_kombat.repository.CharacterRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.getEntityResponseErrorCharacter;
import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.getEntityResponseSuccess;

@Service
@RequiredArgsConstructor
public class CharacterInfoService {
    private final CharacterInfoRepository characterInfoRepository;
    private final FactionService factionService;
    private final CharacterRepository characterRepository;

    public void createCharacterInfo(Long characterId, Long factionId){
        CharacterInfo characterInfo = CharacterInfo.builder()
                .character(characterRepository.findById(characterId).orElseThrow())
                .faction(factionService.getFaction(factionId).orElseThrow())
                .profit(0)
                .build();
        characterInfoRepository.save(characterInfo);
    }

    public EntityResponse getCharacterInfoResponse(Long characterId){
        Optional<CharacterInfo> characterInfo = characterInfoRepository.findById(characterId);
        if (characterInfo.isPresent())
            return getEntityResponseSuccess(characterInfo);
        else
            return getEntityResponseErrorCharacter(characterId);
    }

    public EntityResponse updateProfit(CharacterInfoRequest request){
        Optional<CharacterInfo> characterInfo = characterInfoRepository.findCharacterInfoByCharacter_Id(request.getCharacter().getId());
        if (characterInfo.isPresent()){
            characterInfo.get().setProfit(characterInfo.get().getProfit() + request.getProfit());
            characterInfoRepository.save(characterInfo.get());
            return getEntityResponseSuccess(characterInfo);
        } else
            return getEntityResponseErrorCharacter(request.getCharacter().getId());
    }

    public List<LeaderboardResponse> getProfitLeaderboard() {
        List<CharacterInfo> characterInfos = characterInfoRepository.findAll();
        return characterInfos.parallelStream()
                .map(characterInfo -> new LeaderboardResponse(characterInfo.getCharacter().getId(),
                        characterInfo.getCharacter().getName(), characterInfo.getProfit()))
                .sorted(Comparator.comparingInt(LeaderboardResponse::getProfit).reversed()
                        .thenComparingLong(LeaderboardResponse::getCharacterId))
                .collect(Collectors.toList());
    }
}