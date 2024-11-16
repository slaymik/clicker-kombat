package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.Player;
import ru.rsc.clicker_kombat.model.requests.UserRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.PlayerRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.*;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public List<Player> getAllUsers() {
        return playerRepository.findAll();
    }

    public EntityResponse getUser(Long id) {
        Optional<Player> user = playerRepository.findById(id);
        if (user.isPresent())
            return getEntityResponseSuccess(user.get());
        else
            return getEntityResponseErrorUser(id);

    }

    public EntityResponse createUser(UserRequest response) {
        Player player = Player.builder()
                .id(response.getId())
                .username(response.getUsername())
                .login(response.getLogin())
                .token(response.getToken())
                .registrationDate(Instant.now())
                .lastOnline(null)
                .isActive(true)
                .characters(null)
                .build();
        playerRepository.save(player);
        return getUser(response.getId());
    }


}