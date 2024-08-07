package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.CharacterXp;

public interface CharacterXpRepository extends JpaRepository<CharacterXp,Long> {
}
