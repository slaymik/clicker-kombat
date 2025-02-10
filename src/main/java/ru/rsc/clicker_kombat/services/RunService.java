package ru.rsc.clicker_kombat.services;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsc.clicker_kombat.model.domain.LeaderboardRun;
import ru.rsc.clicker_kombat.model.domain.Player;
import ru.rsc.clicker_kombat.model.domain.Run;
import ru.rsc.clicker_kombat.model.requests.RunActionRequest;
import ru.rsc.clicker_kombat.model.requests.RunStateRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.model.responses.LeaderboardResponse;
import ru.rsc.clicker_kombat.model.responses.PagedElementsResponse;
import ru.rsc.clicker_kombat.repository.LeaderboardRunsRepository;
import ru.rsc.clicker_kombat.repository.RunRepository;
import ru.rsc.clicker_kombat.utils.calcs.UpCoinsCalc;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.rsc.clicker_kombat.consts.EntityResponseFactory.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class RunService {
    private final RunRepository runRepository;
    private final LeaderboardRunsRepository leaderboardRunsRepository;
    private final PlayerService playerService;
    private final CharacterXpService characterXpService;

    @Transactional
    public EntityResponse startRun(RunStateRequest request) {
        Run run = Run.builder()
                .playerId(request.getPlayerId())
                .characterId(request.getCharacterId())
                .characterGameId(request.getCharacterGameId())
                .characterParams(request.getCharacterParams())
                .level(1)
                .stage(1)
                .shitCoins(request.getShitCoins())
                .enemy(request.getEnemy())
                .world(request.getWorld())
                .boosts(request.getBoosts())
                .consumables(request.getConsumables())
                .startTime(Instant.now())
                .isFinished(false)
                .finishedByVictory(false)
                .isHeroic(request.getIsHeroic())
                .build();
        run = runRepository.save(run);
        return EntityResponse.builder().status(SUCCESS).entity(run).build();
    }

    @Transactional
    public ActionResult saveRun(RunStateRequest request) {
        Optional<Run> runOpt = runRepository.findById(request.getRunId());
        if (runOpt.isEmpty()) {
            return getRunNotFoundActionResult(request.getRunId());
        }

        Run run = runOpt.get();

        if (!run.getPlayerId().equals(request.getPlayerId())) {
            return getRunOfOtherPlayerActionResult(run.getId());
        }
        if (Boolean.TRUE.equals(run.getIsFinished())) {
            return getRunAlreadyFinishedActionResult(run.getId());
        }
        if (request.getStage() != run.getStage() + 1
            || request.getLevel() < run.getLevel()
            || request.getLevel() > run.getLevel() + 1) {
            return new ActionResult(false, "Попытка сохранить забег с невалидным уровнем");
        }

        run.setLevel(request.getLevel());
        run.setStage(request.getStage());
        run.setCharacterParams(request.getCharacterParams());
        run.setShitCoins(request.getShitCoins());
        run.setEnemy(request.getEnemy());
        run.setWorld(request.getWorld());
        run.setBoosts(request.getBoosts());
        run.setConsumables(request.getConsumables());
        run.setDuration(request.getDuration());
        runRepository.save(run);
        return new ActionResult(true, "Забег успешно сохранен");
    }

    @Transactional
    public EntityResponse finishRun(RunActionRequest request) {
        Long runId = request.getRunId();
        UUID playerId = request.getPlayerId();

        Optional<Run> runOpt = runRepository.findById(runId);
        if (runOpt.isEmpty()) {
            return getRunNotFoundResponse(runId);
        }

        Run run = runOpt.get();

        if (!run.getPlayerId().equals(playerId)) {
            return getRunOfOtherPlayerResponse(runId);
        }
        if (Boolean.TRUE.equals(run.getIsFinished())) {
            return getRunAlreadyFinishedResponse(runId);
        }

        run.setEndTime(Instant.now());
        run.setIsFinished(true);
        run.setRunUpCoins(UpCoinsCalc
                .calculate(run.getLevel(), run.getIsHeroic(), request.getFinishedByVictory()));
        runRepository.save(run);

        characterXpService.addXp(run.getCharacterId(), run.getRunUpCoins());

        Optional<Player> playerOpt = playerService.findPlayer(playerId);
        if (playerOpt.isEmpty()) {
            return getUserNotFoundResponse(playerId);
        }

        Player player = playerOpt.get();
        player.setUpCoins(player.getUpCoins() + run.getRunUpCoins());
        playerService.savePlayer(player);

        return getEntityResponseSuccess(run);
    }

    @Transactional
    public ActionResult removeUnfinishedRun(Long runId, UUID playerId) {
        Optional<Run> run = runRepository.findById(runId);

        if (run.isEmpty()) {
            return getRunNotFoundActionResult(runId);
        }
        if (!run.get().getPlayerId().equals(playerId)) {
            return getRunOfOtherPlayerActionResult(runId);
        }

        if (Boolean.FALSE.equals(run.get().getIsFinished())) {
            runRepository.deleteById(run.get().getId());
            return new ActionResult(true, "Последний незавершенный забег успешно удален");
        }
        return getRunAlreadyFinishedActionResult(runId);
    }

    public EntityResponse getLastRunByPlayerId(UUID id) {
        Optional<Run> run = runRepository.getTop1ByPlayerIdOrderByStartTimeDesc(id);
        if (run.isPresent()) {
            return getEntityResponseSuccess(run);
        }
        return getEntityResponseSuccess(null);
    }

    public EntityResponse getAllRunsByPlayerId(UUID id, int page, int size) {
        Page<Run> runs = runRepository.findAllByPlayerIdOrderByStartTimeDesc(id, PageRequest.of(page, size));
        return getEntityResponseSuccess(new PagedElementsResponse(runs));
    }

    public EntityResponse getMaxLevelLeaderboard(int size, int page) {
        Page<LeaderboardRun> leaderboard = leaderboardRunsRepository.findLeaderboardRunForAnyCharacter(PageRequest.of(page, size));
        return mapToLeaderboardResponse(leaderboard);
    }

    public EntityResponse getMaxLevelLeaderboardByCharacterId(int size, int page, Integer characterId) {
        Page<LeaderboardRun> leaderboard = leaderboardRunsRepository.findByCharacterGameIdEquals(characterId, PageRequest.of(page, size));
        return mapToLeaderboardResponse(leaderboard);
    }

    private static EntityResponse mapToLeaderboardResponse(Page<LeaderboardRun> leaderboard) {
        List<LeaderboardResponse> leaderboardResponseList = leaderboard.getContent().stream()
                .map(r -> LeaderboardResponse.builder()
                        .runId(r.getRunId())
                        .username(r.getUsername())
                        .playerId(r.getPlayerId())
                        .characterId(r.getCharacterGameId())
                        .level(r.getRun().getLevel())
                        .duration(r.getRun().getDuration())
                        .boostIds(r.getRun().getBoosts().findValues("id").stream().map(JsonNode::asInt).toList())
                        .build()).toList();
        Page<LeaderboardResponse> leaderboardResponses = new PageImpl<>(leaderboardResponseList, leaderboard.getPageable(), leaderboard.getTotalElements());
        return getEntityResponseSuccess(new PagedElementsResponse(leaderboardResponses));
    }
}