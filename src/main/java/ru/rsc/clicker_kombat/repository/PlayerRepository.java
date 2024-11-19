package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.Player;

import java.util.Optional;
import java.util.UUID;


public interface PlayerRepository extends JpaRepository<Player, UUID> {

    Optional<Player> findByLogin(String login);
}