package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsc.clicker_kombat.model.domain.Player;
import ru.rsc.clicker_kombat.model.requests.PlayerRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.PlayerRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.*;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public List<Player> getAllUsers() {
        return playerRepository.findAll();
    }

    public EntityResponse getUser(UUID id) {
        Optional<Player> user = playerRepository.findById(id);
        if (user.isPresent())
            return getEntityResponseSuccess(user.get());
        else
            return getEntityResponseErrorUser(id);

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
}