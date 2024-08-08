package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.Faction;

import java.util.List;

public interface FactionRepository extends JpaRepository<Faction,Long> {
    List<Faction> findFactionsByIsActive(Boolean isActive);
}