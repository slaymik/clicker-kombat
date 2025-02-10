package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.CharacterXp;

import java.util.Optional;

public interface CharacterXpRepository extends JpaRepository<CharacterXp,Integer> {
    Optional<CharacterXp> findCharacterXpByCharacter_Id(Integer characterId);
}
