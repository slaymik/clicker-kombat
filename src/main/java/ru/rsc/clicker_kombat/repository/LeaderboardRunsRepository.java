package ru.rsc.clicker_kombat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rsc.clicker_kombat.model.domain.LeaderboardRun;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeaderboardRunsRepository extends JpaRepository<LeaderboardRun, Long> {

    Page<LeaderboardRun> findByCharacterIdEquals(Integer characterId, Pageable pageable);

    Optional<LeaderboardRun> findByPlayerIdAndCharacterId(UUID playerId, Integer characterId);

    List<LeaderboardRun> findByPlayerId(UUID playerId);

}