package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.Faction;

public interface FactionRepository extends JpaRepository<Faction,Long> {
}