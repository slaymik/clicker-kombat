package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.LeaderboardRun;
import ru.rsc.clicker_kombat.model.domain.Run;
import ru.rsc.clicker_kombat.model.requests.RunStateRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.model.responses.PagedElementsResponse;
import ru.rsc.clicker_kombat.repository.LeaderboardRunsRepository;
import ru.rsc.clicker_kombat.repository.RunRepository;

import java.util.Optional;
import java.util.UUID;

import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.getEntityResponseErrorUser;
import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.getEntityResponseSuccess;


@Service
@RequiredArgsConstructor
public class RunService {
    private final RunRepository runRepository;
    private final LeaderboardRunsRepository leaderboardRunsRepository;

    public EntityResponse saveRun(RunStateRequest request) {
        Run run = Run.builder()
                .playerId(request.getPlayer_id())
                .character(request.getCharacter())
                .level(request.getLevel())
                .shitCoins(request.getShitCoins())
                .runUpCoins(request.getRunUpCoins())
                .enemy(request.getEnemy())
                .world(request.getWorld())
                .boosts(request.getBoosts())
                .consumables(request.getConsumables())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .duration(request.getDuration())
                .isEnded(request.getIsEnded())
                .build();
        runRepository.save(run);
        return getEntityResponseSuccess(run);
    }

    public EntityResponse getLastRunByPlayerId(UUID id) {
        Optional<Run> run = runRepository.getTop1ByPlayerIdOrderByStartTimeDesc(id);
        if (run.isPresent()) {
            return getEntityResponseSuccess(run);
        }
        return getEntityResponseErrorUser(id);
    }

    public EntityResponse getAllRunsByPlayerId(UUID id, int page, int size) {
        Page<Run> runs = runRepository.findAllByPlayerIdOrderByStartTimeDesc(id, PageRequest.of(page, size));
        return getEntityResponseSuccess(new PagedElementsResponse(runs));
    }

    public EntityResponse getMaxLevelLeaderboard(int size, int page) {
        Page<LeaderboardRun> leaderboard = leaderboardRunsRepository.findAll(PageRequest.of(page, size));
        return getEntityResponseSuccess(new PagedElementsResponse(leaderboard));
    }

    public EntityResponse getMaxLevelLeaderboardByCharacterId(int size, int page, Integer characterId){
        Page<LeaderboardRun> leaderboard = leaderboardRunsRepository.findByCharacterIdEquals(characterId, PageRequest.of(page,size));
        return getEntityResponseSuccess(new PagedElementsResponse(leaderboard));
    }
}
