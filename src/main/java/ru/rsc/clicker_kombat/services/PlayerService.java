package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsc.clicker_kombat.model.domain.LeaderboardRun;
import ru.rsc.clicker_kombat.model.domain.Player;
import ru.rsc.clicker_kombat.model.domain.PlayerRating;
import ru.rsc.clicker_kombat.model.requests.PlayerRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.model.responses.PagedElementsResponse;
import ru.rsc.clicker_kombat.model.responses.PlayerResponse;
import ru.rsc.clicker_kombat.repository.LeaderboardRunsRepository;
import ru.rsc.clicker_kombat.repository.PlayerRatingRepository;
import ru.rsc.clicker_kombat.repository.PlayerRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.getEntityResponseErrorUser;
import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.getEntityResponseSuccess;
import static ru.rsc.clicker_kombat.consts.PlayerConsts.NOT_HEROIC_RATING_LIMIT;
import static ru.rsc.clicker_kombat.utils.calcs.RatingCalc.calculateRating;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final LeaderboardRunsRepository leaderboardRepository;
    private final PlayerRatingRepository playerRatingRepository;

    public List<Player> getAllUsers() {
        return playerRepository.findAll();
    }

    public EntityResponse getUser(UUID id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        Optional<PlayerRating> playerRatingOptional = playerRatingRepository.findById(id);
        if (playerOptional.isEmpty() || playerRatingOptional.isEmpty())
            return getEntityResponseErrorUser(id);
        else {
            Player player = playerOptional.get();
            PlayerRating playerRating = playerRatingOptional.get();
            return getEntityResponseSuccess(PlayerResponse.builder()
                    .playerId(player.getId())
                    .username(player.getUsername())
                    .rating(player.getRating())
                    .rank(playerRating.getRank())
                    .upCoins(player.getUpCoins())
                    .build());
        }
    }

    public Optional<Player> getPlayerIdByLogin(String login) {
        return playerRepository.findByLogin(login);
    }

    public void createUser(String login, String username) {
        Player player = Player.builder()
                .registrationDate(Instant.now())
                .login(login)
                .username(username)
                .lastOnline(null)
                .isActive(true)
                .upCoins(0L)
                .characters(null)
                .build();
        playerRepository.save(player);
    }

    @Transactional
    public ActionResult updatePlayer(PlayerRequest request) {
        Optional<Player> playerOpt = playerRepository.findById(request.getId());
        if (playerOpt.isEmpty()) {
            return new ActionResult(false, "Пользователь с id:%s не найден".formatted(request.getId().toString()));
        }
        Player player = playerOpt.get();
        player.setUpCoins(request.getUpCoins());
        player.setLastOnline(request.getLastOnline());
        playerRepository.save(player);
        return new ActionResult(true, "Пользователь обновлен");
    }

    public ActionResult addSession(UUID id) {
        Optional<Player> playerOpt = playerRepository.findById(id);
        if (playerOpt.isEmpty()) {
            return new ActionResult(false, "Пользователь с id:%s не найден".formatted(id.toString()));
        }
        Player player = playerOpt.get();
        player.setSession(player.getSession() + 1);
        playerRepository.save(player);
        return new ActionResult(true, "Сессия добавлена");
    }

    @Transactional
    public void updateRating(UUID playerId) {
        List<LeaderboardRun> leaderboardRuns = leaderboardRepository.findByPlayerId(playerId);
        if (leaderboardRuns.isEmpty()) {
            return;
        }
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isEmpty()) {
            return;
        }
        Player player = playerOpt.get();
        player.setRating(calculateRating(leaderboardRuns, NOT_HEROIC_RATING_LIMIT));
        playerRepository.save(player);
    }

    public EntityResponse getRankLeaderboard(Integer size, Integer page){
        Page<PlayerRating> leaderboard = playerRatingRepository.findAll(PageRequest.of(page, size));
        return getEntityResponseSuccess(new PagedElementsResponse(leaderboard));
    }
}