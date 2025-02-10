package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.Character;

import java.util.List;
import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, Integer> {

    List<Character> getCharactersByPlayer_Id(UUID id);
}