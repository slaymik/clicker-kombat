package ru.rsc.clicker_kombat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.Run;

import java.util.Optional;
import java.util.UUID;

public interface RunRepository extends JpaRepository<Run, Long> {
    Optional<Run> getTop1ByPlayerIdOrderByStartTimeDesc(UUID playerId);

    Page<Run> findAllByPlayerIdOrderByStartTimeDesc(UUID id, Pageable pageable);
}